<script lang="ts">
    import axios from "axios";
    import { link, push, replace } from "svelte-spa-router";
    import { noti } from "../../option/store";

    // 변수
    let idVal:string = "";
    let passVal:string = "";
    let passChkVal:string = "";
    let nameVal:string = "";
    let emailVal:string = "";
    let phoneVal:string = "";

    let isId:boolean = false;
    let isPass:boolean = false;
    let isPassChk:boolean = false;
    let isName:boolean = false;
    let isEmail:boolean = false;
    let isPhone:boolean = false;

    let idInpColor = { color: "border-color:#000"}
    let emailInpColor = { color: "border-color:#000"}
    let passInpColor = { color: "border-color:#000"}
    let passChkInpColor = { color: "border-color:#000"}
    let nameInpColor = { color: "border-color:#000"}
    let phoneInpColor = { color: "border-color:#000"}

    // Method
    // ID
    const idChk = () => {
        let idReg = /^[a-zA-z0-9]{2,12}$/
        let idRegTo = /admin|manager|test|tester/g;
        if(!idVal){
            isId = false;
            idInpColor.color = "border-color:#000";
            
        }else if(idVal.length === 1){
            isId = false;
            idInpColor.color = "border-color:#ff4a4a";
            
        }else if(!idReg.test(idVal) || idRegTo.test(idVal)){
            isId = false;
            idInpColor.color = "border-color:#ff4a4a";
            
        }else{
            id();
        }
    }

    // 아이디 중복 체크 
    const id = async () => {
        await axios.get("/api/user/id/"+idVal).then((res)=>{
            let result = res.data;
            //console.log("ID EXIST SUCCESS\n"+result)
            if(result){
                //console.log("아이디 중복 입니다")
                isId = false;
                idInpColor.color = "border-color:#ff4a4a";
                
            }else{   
                //console.log("아이디 사용 가능")
                isId = true;
                idInpColor.color = "border-color:#5992ff";
                
            }
        }).catch((err) => {});
    }

    // Password
    const passCheck = () => {
        let passReg = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/
        if(!passVal){
            //console.log("패스워드 칸 공백")
            isPass = false;
            passInpColor.color = "border-color:#000";
            
        }else if(!passReg.test(passVal)){
            //console.log("특수문자 1개, 영문, 숫자  포함 8자리 이상 입력 해 주세요")
            isPass = false;
            passInpColor.color = "border-color:#ff4a4a";
           
        }else{
            //console.log("OK")
            isPass = true;
            passInpColor.color = "border-color:#5992ff";
            
        }
    }

    // Password Check
    const passCheckCheck = () => {
        if(!passChkVal){
            //console.log("패스워드 확인 칸 공백")
            isPassChk = false;
            passChkInpColor.color = "border-color:#000";
            
        }else if(passChkVal !== passVal){
            //console.log("패스워드를 위의 창과 같게 입력 해 주세요")
            isPassChk = false;
            passChkInpColor.color = "border-color:#ff4a4a";
            
        }else{
            //console.log("OK")
            isPassChk = true;          
            passChkInpColor.color = "border-color:#5992ff";

        }
    }

    // Name
    const nameChk = () => {
        let nameReg = /^[a-zA-z가-힣]{1,12}$/
        let nameRegTo = /관리자|매니저|매니져|테스터/g;
        if(!nameVal){
            isName = false;
            nameInpColor.color = "border-color:#000";
            
        }else if(nameVal.length === 1){
            isName = false;
            nameInpColor.color = "border-color:#ff4a4a";
            
        }else if(!nameReg.test(nameVal) || nameRegTo.test(nameVal)){
            isName = false;
            nameInpColor.color = "border-color:#ff4a4a";
            
        }else{
            isName = true;
            nameInpColor.color = "border-color:#5992ff";
            
        }
    }

    // Email
    // 이메일 정규식 체크
    const emailChk = () => {
        let emailReg = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/
        if(!emailVal){
            //console.log("이메일 칸 공백");
            isEmail = false;
            emailInpColor.color = "border-color:#000";
           
        }else if(!emailReg.test(emailVal)){
            //console.log("이메일을 정확히 입력 해 주세요")
            isEmail = false;
            emailInpColor.color = "border-color:#ff4a4a";
            
        }else{
            email();
        }

    }
    // 이메일 중복 체크 
    const email = async () => {
        await axios.get("/api/user/email/"+emailVal).then((res)=>{
            let result = res.data;
            //console.log("EMAIL EXIST SUCCESS\n"+result)
            if(result){
                //console.log("이메일 중복 입니다")
                isEmail = false;
                emailInpColor.color = "border-color:#ff4a4a";
                
            }else{   
                //console.log("이메일 사용 가능")
                isEmail = true;
                emailInpColor.color = "border-color:#5992ff";
                
            }
        }).catch((err) => {});
    }

    // Phone
    // 핸드폰 번호 자동 하이픈 
    const autoHyphen = () => {
        //console.log(userPhone)
        phoneVal = phoneVal.replace(/[^0-9]/g, '').replace(/^(\d{0,3})(\d{0,4})(\d{0,4})$/g, "$1-$2-$3").replace(/(\-{1,2})$/g, "");
    }
    // 핸드폰 정규식 체크
    const phoneChk = () => {
        let phoneReg = /^\d{3}-\d{3,4}-\d{4}$/
        if(!phoneVal){
            //console.log("핸드폰 칸 공백")
            isPhone = false;
            phoneInpColor.color = "border-color:#000";

        }else if(!phoneReg.test(phoneVal)){
            //console.log("핸드폰을 정확히 입력 해 주세요")
            isPhone = false;
            phoneInpColor.color = "border-color:#ff4a4a";

        }else{
            phone();
        }

    }
    // 핸드폰 중복체크
    const phone = async () => {
        await axios.get("/api/user/phone/"+phoneVal).then((res)=>{
            let result = res.data;
            //console.log(result)
            if(result){
                isPhone = false;
                phoneInpColor.color = "border-color:#ff4a4a";
                
            }else{   
                isPhone = true;
                phoneInpColor.color = "border-color:#5992ff";                
            }
      
        }).catch((err) => {});
    }

    // Register Button
    const registerBtn = () => {

        if(!idVal || !isId){
            alert("아이디을 확인 해 주세요")
        }else if(!emailVal || !isEmail){
            alert("이메일을 확인 해 주세요")
        }else if(!passVal || !isPass){
            alert("패스워드를 확인 해 주세요")
        }else if(!passChkVal || !isPassChk){
            alert("패스워드 확인을 입력 해 주세요")
        }else if(!nameVal || !isName){
            alert("이름을 확인 해 주세요")
        }else if(!phoneVal || !isPhone){
            alert("핸드폰을 확인 해 주세요")
        }else if(idVal && isId && emailVal && isEmail && passVal && isPass && passChkVal && isPassChk && nameVal && isName && phoneVal && isPhone){
            register();
        }else{
            alert("회원가입 ERROR [404]\nredrat05@naver.com에 문의 해 주세요")
        }

    }

    // 회원가입 완료 
    const register = async () => {
    let datas = {
        user_id : idVal.trim(),
        user_pass : passVal.trim(),
        user_nm : nameVal.trim(),
        user_email : emailVal.trim(),
        user_phone : phoneVal.trim(),
    }

    await axios.post("/api/user", datas).then((res)=>{
        if(res.data === 1){
            replace("/")
            noti.success("회원가입 완료", 2000)
        }
    }).catch((err)=>{});

    }

</script>

<div class="layout-480">
    <div class="mt-50">
        <div class="mt-20 ta-c">
            <a href="/" class="fs-125rem f-nato-bold" use:link>Product Management 회원가입</a>
        </div>

        <!-- 아이디 -->
        <div class="mt-50">
            <p class="fs-16 fw-b">아이디</p>
            <div class="mt-10">
                <input type="text" class="fs-16 pretendard-regular padding-12-16 width-100 border-black border-radius-8" style="{idInpColor.color}" bind:value={idVal} on:input={idChk} placeholder="2자리 이상 (영문 포함) 입력"/>
            </div>
        </div>
        
        <!-- 패스워드 -->
        <div class="mt-20">
            <p class="fs-16 fw-b">비밀번호</p>
            <div class="mt-10">
                <input type="password" class="fs-16 pretendard-regular padding-12-16 width-100 border-black border-radius-8" style="{passInpColor.color}" bind:value={passVal} on:input={passCheck} placeholder="특수문자 1개, 영문, 숫자 포함 8자리 이상 입력"/>
            </div>
        </div>

        <!-- 패스워드 확인 -->
        <div class="mt-20">
            <p class="fs-16 fw-b">비밀번호 확인</p>
            <div class="mt-10">
                <input type="password" class="fs-16 pretendard-regular padding-12-16 width-100 border-black border-radius-8" style="{passChkInpColor.color}" bind:value={passChkVal} on:input={passCheckCheck} placeholder="패스워드 확인 입력"/>
            </div>
        </div>

        <!-- 이름 -->
        <div class="mt-20">
            <p class="fs-16 fw-b">이름</p>
            <div class="mt-10">
                <input type="text" class="fs-16 pretendard-regular padding-12-16 width-100 border-black border-radius-8" style="{nameInpColor.color}" bind:value={nameVal} on:input={nameChk} placeholder="이름 입력"/>
            </div>
        </div>

        <!-- 핸드폰 -->
        <div class="mt-20">
            <p class="fs-16 fw-b">핸드폰</p>
            <div class="mt-10">
                <input type="text" class="fs-16 pretendard-regular padding-12-16 width-100 border-black border-radius-8" style="{phoneInpColor.color}" bind:value={phoneVal} maxlength="13" on:input={autoHyphen} on:input={phoneChk} placeholder="숫자만 입력"/>
            </div>
        </div>

        <!-- 이메일 -->
        <div class="mt-20">
            <p class="fs-16 fw-b">이메일</p>
            <div class="mt-10">
                <input type="email" class="fs-16 pretendard-regular padding-12-16 width-100 border-black border-radius-8" style="{emailInpColor.color}" bind:value={emailVal} on:input={emailChk} placeholder="이메일 입력"/>
            </div>
        </div>

        <div class="mt-30">
            <button type="button" class="fs-16 pretendard-regular padding-12-16 width-100 border-black border-radius-8 background-none button-primary" on:click={registerBtn}>회원가입</button>
        </div>

        <div style="height: 150px;"></div>

    </div>

</div>