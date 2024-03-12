<script lang="ts">
    import axios from "axios";
    import { push } from "svelte-spa-router";

    let userEmail:string = "";
    let msg:string = "";

    let promise:any;
    const findIdBtn = () => {
        let emailReg = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/
        if(!userEmail){
            alert("이메일을 입력 해 주세요")
        }else if(!emailReg.test(userEmail)){
            msg = "이메일을 정확히 입력 해 주세요";
        }else{
            promise = findId();
        }
    }

    const findId = async () => {
        let datas = {
            user_email : userEmail,
        }
        await axios.post("/api/user/id",datas).then((res)=>{
            if(res.data){
                msg = "정상적으로 이메일이 발송 되었습니다";
            }else{
                msg = "가입하신 이력이 없는 이메일 입니다";
            }
        }).catch((err)=>{});
    }
</script>

<div class="layout-480">

    <div class="padding-12">
        <!-- Title -->
        <div class="mt-10 padding-12-16 box-shadow-primary background-color-white">
            <div class="mt-20 ta-c">
                <p class="fs-20 pretendard-bold">아이디 찾기</p>
            </div>
            <div class="mt-20 ta-c">
                <p class="fs-18 pretendard-regular">가입당시 입력한 이메일을 입력 해 주세요</p>
            </div>
            <div class="mt-30 ta-c">
                <input type="email" class="fs-16 pretendard-regular width-100 border-default padding-12-16 border-radius-8" bind:value={userEmail} />
            </div>
            <div class="mt-10 ta-c">
                {#await promise}
                    <p>...waiting</p>
                {:then number}
                    <p class="fs-16 pretendard-regular" style="color:#ff4a4a;">{msg}</p>
                {/await}
            </div>
            <div class="mt-30 ta-c">
                <button type="button" class="fs-1rem pretendard-regular background-none border-black padding-8-16" on:click={()=>{push("/login")}}>취소</button>
                <button type="button" class="fs-1rem pretendard-regular background-none border-black padding-8-16" on:click={findIdBtn}>확인</button>
            </div>
        </div>
    </div>

</div>