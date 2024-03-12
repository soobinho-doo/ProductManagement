<script lang="ts">
    import axios from "axios";
    import { push } from "svelte-spa-router";

    let stepNumber:number = 0;
    let userEmail:string = "";
    let msg:string = "";

    const findPassBtn = () => {
        let emailReg = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/
        if(!userEmail){
            alert("이메일을 입력 해 주세요")
        }else if(!emailReg.test(userEmail)){
            msg = "이메일을 정확히 입력 해 주세요";
        }else{
            findPass();
        }
    }

    let randomNumber:number = 0;
    const findPass = async () => {
        let datas = {
            user_email : userEmail,
        }
        await axios.post("/api/user/pass",datas).then((res)=>{
            if(res.data !== 0){
                msg = "정상적으로 이메일이 발송 되었습니다";
                randomNumber = res.data;
                stepNumber = 1;
            }else{
                msg = "가입하신 이력이 없는 이메일 입니다";
            }
        }).catch((err)=>{});
    }

    // 2 Step
    let strVal:number;
    let twoMsg:string = ""; 
    const randomNumberCheck = () => {
        //console.log("1: "+randomNumber+" 2: "+strVal);
        if(randomNumber === strVal){
            //alert("확인완료");
            stepNumber = 2;
            twoMsg = "";
        }else{
            //alert("실패");
            twoMsg = "인증코드를 다시 한 번 확인해주세요";
        }
    }

    // 3 Step
    // Password
    let passVal = "";
    let passChkVal = "";
    let isPass:boolean = false;
    let isPassChk:boolean = false;
    let threeMsg:string = "";
    const passCheck = () => {
        let passReg = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/
        if(!passVal){
            //console.log("패스워드 칸 공백")
            isPass = false;
        }else if(!passReg.test(passVal)){
            //console.log("특수문자 1개, 영문, 숫자  포함 8자리 이상 입력 해 주세요")
            isPass = false;
        }else{
            //console.log("OK")
            isPass = true;
        }
    }
    // Password Check
    const passCheckCheck = () => {
        if(!passChkVal){
            //console.log("패스워드 확인 칸 공백")
            isPassChk = false;
        }else if(passChkVal !== passVal){
            //console.log("패스워드를 위의 창과 같게 입력 해 주세요")
            isPassChk = false;
        }else{
            //console.log("OK")
            isPassChk = true;
        }
    }

    const changePassBtn = () => {
        if(!isPass){
            alert("패스워드를 다시 한 번 확인해주세요\n특수문자 1개, 영문, 숫자  포함 8자리 이상 입력")
        }else if(!isPassChk){
            alert("패스워드 확인란을 다시 한 번 확인해주세요")
        }else {
            changePass();
        }
    }
    const changePass = async () => {
        let datas = {
            user_pass: passVal, 
            user_email: userEmail
        }
        await axios.post("/api/user/change/pass",datas).then((res)=>{
            console.log("RESPONSE "+res.data);
            if(res.data === 1){
                push("/login")
            }
        }).catch((err)=>{});
    }

</script>

<div class="layout-480">

    <div class="padding-12">
        <!-- Title -->
        <div class="mt-10 padding-12-16 box-shadow-primary background-color-white">
            <div class="mt-20 ta-c">
                <p class="fs-20 pretendard-bold">패스워드 찾기</p>
            </div>

            {#if stepNumber === 0}
                <div class="mt-20 ta-c">
                    <p class="fs-18 pretendard-regular">가입당시 입력한 이메일을 입력 해 주세요</p>
                </div>
                <div class="mt-30 ta-c">
                    <input type="email" class="fs-16 border-default pretendard-regular width-100 padding-12-16 border-radius-8" bind:value={userEmail} />
                </div>
                <div class="mt-10 ta-c">
                    <p class="fs-16 pretendard-regular" style="color:#ff4a4a;">{msg}</p>
                </div>
                <div class="mt-30 ta-c">
                    <button type="button" class="fs-1rem pretendard-regular background-none border-black padding-8-16" on:click={()=>{push("/login")}}>취소</button>
                    <button type="button" class="fs-1rem pretendard-regular background-none border-black padding-8-16" on:click={findPassBtn}>확인</button>
                </div>
            {:else if stepNumber === 1}
                <div class="mt-20 ta-c">
                    <p class="fs-18 pretendard-regular">이메일로 발송드린 인증코드 4자리를 입력 해 주세요</p>
                </div>
                <div class="mt-30 ta-c">
                    <input type="number" class="fs-16 border-default pretendard-regular width-100 padding-12-16 border-radius-8" bind:value={strVal} placeholder="인증번호 입력"/>
                </div>
                <div class="mt-10 ta-c">
                    <p class="fs-16 pretendard-regular" style="color:#ff4a4a;">{twoMsg}</p>
                </div>
                <div class="mt-30 ta-c">
                    <button type="button" class="fs-1rem pretendard-regular background-none border-black padding-8-16" on:click={randomNumberCheck}>확인</button>
                </div>
            {:else if stepNumber === 2}
                <div class="mt-20 ta-c">
                    <p class="fs-18 pretendard-regular">변경하실 비밀번호를 입력 해 주세요</p>
                </div>
                <div class="mt-30 ta-c">
                    <input type="password" class="fs-16 border-default pretendard-regular width-100 padding-12-16 border-radius-8" bind:value={passVal} on:input={passCheck} placeholder="특수문자 1개, 영문, 숫자 포함 8자리 이상 입력"/>
                </div>
                <div class="mt-10 ta-c">
                    <input type="password" class="fs-16 border-default pretendard-regular width-100 padding-12-16 border-radius-8" bind:value={passChkVal} on:input={passCheckCheck} placeholder="패스워드 확인 입력"/>
                </div>
                <div class="mt-10 ta-c">
                    <p class="fs-16 pretendard-regular" style="color:#ff4a4a;">{threeMsg}</p>
                </div>
                <div class="mt-30 ta-c">
                    <button type="button" class="fs-1rem pretendard-regular background-none border-black padding-8-16" on:click={changePassBtn}>변경</button>
                </div>
            {/if}
            

        </div>
    </div>

</div>

<style>
    input[type="number"]::-webkit-outer-spin-button,
    input[type="number"]::-webkit-inner-spin-button {
        -webkit-appearance: none;
        margin: 0;
    }
</style>