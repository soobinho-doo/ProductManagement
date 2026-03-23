<script lang="ts">
    import { onMount } from "svelte";
    import { link, push, replace } from 'svelte-spa-router';
    import { auth } from "../../option/auth";

    // 변수
    let userId:string = "";
    let userPass:string = "";
    let errorMsg:string = "";
    
    let userIdElement:HTMLElement;
    let userPassElement:HTMLInputElement;

    // Method
    onMount(() => {
        userIdElement.focus();
    });

    // Enter 이벤트 
    const enterEvent = (e:any) => {
        if(e.keyCode === 13){
            loginBtn();
        }
    }

    const loginBtn = () => {
        if(!userId){
            alert("아이디를 입력 해 주세요");
            userIdElement.focus();

        }else if(!userPass){
            alert("패스워드를 입력 해 주세요");
            userPassElement.focus();

        }else{
            login();
        }
    }
    const login = async () => {
        let data = {
            user_id: userId,
            user_pass:userPass
        }
        const loginResult:any = await auth.login(data);
        errorMsg = loginResult;
    }

    // Option //
    // 패스워드 확인 체크
    let isShowPass:boolean = true;
    const passChange = () => {
        isShowPass = !isShowPass;
        userPassElement.type = isShowPass ? "password" : "text"
    }

</script>

<div class="p-100-0">

    <div class="layout-480">
    <!--  -->
        <div class="mt-20 display-flex align-items justify-content-between">
            <div class="display-grid align-items">
                <div class="display-flex align-items gap-5">
                    <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32">
                        <path  d="M10 18h8v2h-8zm0-5h12v2H10zm0 10h5v2h-5z"/><path fill="currentColor" d="M25 5h-3V4a2 2 0 0 0-2-2h-8a2 2 0 0 0-2 2v1H7a2 2 0 0 0-2 2v21a2 2 0 0 0 2 2h18a2 2 0 0 0 2-2V7a2 2 0 0 0-2-2ZM12 4h8v4h-8Zm13 24H7V7h3v3h12V7h3Z"/>
                    </svg>
                    <span class="fs-18 pretendard-bold">Product Management</span>
                </div>
                <div class="padding-6">
                    <h4 class="pretendard-regular color-b1">효율적인 자산 관리를 위한 상품 재고 관리 WEB (Free)</h4>
                </div>
            </div>

            <div>
                <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24">
                    <g fill="none" fill-rule="evenodd">
                        <path d="m12.594 23.258l-.012.002l-.071.035l-.02.004l-.014-.004l-.071-.036q-.016-.004-.024.006l-.004.01l-.017.428l.005.02l.01.013l.104.074l.015.004l.012-.004l.104-.074l.012-.016l.004-.017l-.017-.427q-.004-.016-.016-.018m.264-.113l-.014.002l-.184.093l-.01.01l-.003.011l.018.43l.005.012l.008.008l.201.092q.019.005.029-.008l.004-.014l-.034-.614q-.005-.019-.02-.022m-.715.002a.02.02 0 0 0-.027.006l-.006.014l-.034.614q.001.018.017.024l.015-.002l.201-.093l.01-.008l.003-.011l.018-.43l-.003-.012l-.01-.01z" />
                        <path fill="#b1b1b1" d="M4 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v16a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2zm14 0H6v16h12zm-6.452 2.763a1 1 0 0 1 0 1.414L9.603 10.12a1.25 1.25 0 0 1-1.767 0l-.884-.884a1 1 0 1 1 1.414-1.414l.354.354l1.414-1.414a1 1 0 0 1 1.414 0ZM13 9a1 1 0 0 1 1-1h2a1 1 0 1 1 0 2h-2a1 1 0 0 1-1-1m-6 4.5A1.5 1.5 0 0 1 8.5 12h2a1.5 1.5 0 0 1 1.5 1.5v2a1.5 1.5 0 0 1-1.5 1.5h-2A1.5 1.5 0 0 1 7 15.5zm2 .5v1h1v-1zm4 .5a1 1 0 0 1 1-1h2a1 1 0 1 1 0 2h-2a1 1 0 0 1-1-1" />
                    </g>
                </svg>
            </div>
        </div>
    </div>

    <!--  -->
    <div style="background-color: #2A3F54; padding: 16px 0px">
        <div class="layout-480">
            <div class="mt-30">
                <div class="mt-10">
                    <input type="text" class="fs-1rem pretendard-regular padding-12-16 width-100 border-none border-radius-4 background-color-b1 inp-primary" bind:value={userId} bind:this={userIdElement} placeholder="아이디"/>
                </div>
            </div>
            
            <div class="mt-20">
                <div class="mt-10 position-relative">
                    <input type="password" class="fs-1rem pretendard-regular padding-12-16 width-100 border-none border-radius-4 background-color-b1 inp-primary" bind:value={userPass} bind:this={userPassElement} placeholder="패스워드"/>
                    <button type="button" class="background-none border-none pass-icon" on:click={passChange}>
                        {#if isShowPass}
                            <svg xmlns="http://www.w3.org/2000/svg" class="svg-color-change" width="22" height="22" viewBox="0 0 576 512">
                                <path d="M288 80c-65.2 0-118.8 29.6-159.9 67.7C89.6 183.5 63 226 49.4 256c13.6 30 40.2 72.5 78.6 108.3C169.2 402.4 222.8 432 288 432s118.8-29.6 159.9-67.7C486.4 328.5 513 286 526.6 256c-13.6-30-40.2-72.5-78.6-108.3C406.8 109.6 353.2 80 288 80zM95.4 112.6C142.5 68.8 207.2 32 288 32s145.5 36.8 192.6 80.6c46.8 43.5 78.1 95.4 93 131.1c3.3 7.9 3.3 16.7 0 24.6c-14.9 35.7-46.2 87.7-93 131.1C433.5 443.2 368.8 480 288 480s-145.5-36.8-192.6-80.6C48.6 356 17.3 304 2.5 268.3c-3.3-7.9-3.3-16.7 0-24.6C17.3 208 48.6 156 95.4 112.6zM288 336c44.2 0 80-35.8 80-80s-35.8-80-80-80c-.7 0-1.3 0-2 0c1.3 5.1 2 10.5 2 16c0 35.3-28.7 64-64 64c-5.5 0-10.9-.7-16-2c0 .7 0 1.3 0 2c0 44.2 35.8 80 80 80zm0-208a128 128 0 1 1 0 256 128 128 0 1 1 0-256z"/>
                            </svg>
                        {:else}
                            <svg xmlns="http://www.w3.org/2000/svg" class="svg-color-change" width="22" height="22" viewBox="0 0 640 512">
                                <path d="M38.8 5.1C28.4-3.1 13.3-1.2 5.1 9.2S-1.2 34.7 9.2 42.9l592 464c10.4 8.2 25.5 6.3 33.7-4.1s6.3-25.5-4.1-33.7L525.6 386.7c39.6-40.6 66.4-86.1 79.9-118.4c3.3-7.9 3.3-16.7 0-24.6c-14.9-35.7-46.2-87.7-93-131.1C465.5 68.8 400.8 32 320 32c-68.2 0-125 26.3-169.3 60.8L38.8 5.1zm151 118.3C226 97.7 269.5 80 320 80c65.2 0 118.8 29.6 159.9 67.7C518.4 183.5 545 226 558.6 256c-12.6 28-36.6 66.8-70.9 100.9l-53.8-42.2c9.1-17.6 14.2-37.5 14.2-58.7c0-70.7-57.3-128-128-128c-32.2 0-61.7 11.9-84.2 31.5l-46.1-36.1zM394.9 284.2l-81.5-63.9c4.2-8.5 6.6-18.2 6.6-28.3c0-5.5-.7-10.9-2-16c.7 0 1.3 0 2 0c44.2 0 80 35.8 80 80c0 9.9-1.8 19.4-5.1 28.2zm9.4 130.3C378.8 425.4 350.7 432 320 432c-65.2 0-118.8-29.6-159.9-67.7C121.6 328.5 95 286 81.4 256c8.3-18.4 21.5-41.5 39.4-64.8L83.1 161.5C60.3 191.2 44 220.8 34.5 243.7c-3.3 7.9-3.3 16.7 0 24.6c14.9 35.7 46.2 87.7 93 131.1C174.5 443.2 239.2 480 320 480c47.8 0 89.9-12.9 126.2-32.5l-41.9-33zM192 256c0 70.7 57.3 128 128 128c13.3 0 26.1-2 38.2-5.8L302 334c-23.5-5.4-43.1-21.2-53.7-42.3l-56.1-44.2c-.2 2.8-.3 5.6-.3 8.5z"/>
                            </svg>
                        {/if}
                    </button>
                </div>
            </div>

            <div class="mt-10 ta-c">
                <p class="fs-18 pretendard-regular" style="color:#ff4a4a;">{errorMsg}</p>
            </div>

            <div class="mt-50">
                <button type="button" class="fs-1rem pretendard-regular padding-12-16 width-100 border-none border-radius-4 background-none button-second" on:click={loginBtn}>로그인</button>
            </div>
            <div class="mt-10">
                <button type="button" class="fs-1rem pretendard-regular padding-12-16 width-100 border-none border-radius-4 background-none button-second" on:click={() => {push("/register")}}>회원가입</button>
            </div>
            <div class="mt-20">
                <div class="display-flex align-items justify-content-right gap-10">
                    <a href="/forgot/id" class="fs-14 pretendard-regular color-white" use:link>아이디 찾기</a>
                    <sapn class="fs-14 pretendard-regular color-white">|</sapn>
                    <a href="/forgot/pass" class="fs-14 pretendard-regular color-white" use:link>비밀번호 찾기</a>
                </div>
            </div>
        </div>
    </div>

    <div style="height: 150px;"></div>

</div>
    

<svelte:window on:keydown={enterEvent}/>

<style>
    .pass-icon {position: absolute; top: 12px; right: 12px;}
</style>
