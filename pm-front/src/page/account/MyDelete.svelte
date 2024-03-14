<script lang="ts">
    import axios from "axios";

    // 1 Step
    let oneStep:boolean = false;
    let oneStepCheck:boolean = false; 
    const oneStepBtn = () => {
        if(!oneStepCheck){
            alert("동의란에 체크 해 주세요");
            oneStep = false;
        }else{
            oneStep = true;
        }
    }

    // 2 Step
    let passVal = "";
    let errorMsg = "";

    const twoStepBtn = () => {
        if(!passVal){
            alert("패스워드를 입력 해 주세요")
        }else{
            deleteUser();
        }
    }

    // 유저의 비밀번호가 일치하는지 
    const deleteUser = async () => {
        let data = {
            user_pass: passVal,
        }
    }

     
</script>

<div class="padding-6">
    <div>
        <p class="fs-18 pretendard-bold">회원 탈퇴</p>
    </div>

    <!--  -->
    {#if !oneStep}
        <!-- 1 Step -->
        <div class="mt-30">
            <p class="fs-14 pretendard-regular">■ 탈퇴한 아이디는 본인과 타인 모두 <span class="strong fs-16 pretendard-regular">재사용 및 복구가 불가</span>하오니 신중하게 선택하시기 바랍니다.</p>
        </div>
        <div class="mt-20">
            <p class="fs-14 pretendard-regular">■ 탈퇴 후에도 게시판형 서비스에 등록한 게시물은 그대로 남아 있습니다.</p>
            <p class="mt-5 fs-14 pretendard-regular underline padding-left14">삭제를 원하는 게시글이 있다면 반드시 탈퇴 전 삭제하시기 바랍니다.</p>
        </div>
        <div class="mt-30 flex gap10">
            <input type="checkbox" id="deleteAgree" class="fs-16 pretendard-bold" bind:checked={oneStepCheck}/>
            <label for="deleteAgree" class="fs-16 pretendard-bold">안내 사항을 모두 확인하였으며, 이에 동의합니다.</label>
        </div>
        <div class="mt-30 ta-c">
            <button type="button" class="fs-16 pretendard-regular background-none border-black padding-8-16" on:click={oneStepBtn}>확인</button>
        </div>
    {:else} 
        <!-- 2 Step -->
        <div class="mt-20">
            <p class="fs-16 pretendard-regular">비밀번호 확인란을 입력하시면 탈퇴가 마무리 됩니다</p>
        </div>
        <div class="mt-30 ta-c">
            <input type="password" class="fs-16 border-default pretendard-regular padding-12-16 border-radius-8" bind:value={passVal} />
        </div>
        <div class="mt-10 ta-c">
            <p class="fs-16 pretendard-regular" style="color:#ff4a4a;">{errorMsg}</p>
        </div>
        <div class="mt-30 ta-c">
            <button type="button" class="fs-16 pretendard-regular background-none border-black padding-8-16" on:click={twoStepBtn}>탈퇴</button>
        </div>
    {/if}
    
</div>

<style>
    .strong {color: #e8505a;}
    .underline {text-decoration: underline;}
    .padding-left14 {padding-left: 14px;}
</style>