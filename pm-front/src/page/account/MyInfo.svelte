<script lang="ts">
    import LoadingPage from "../../loading/LoadingPage.svelte";
    import ErrorAxiosPage from "../error/ErrorAxiosPage.svelte";
    import { account } from "../../option/account";

    // 변수
    let userTB:any = []

    // Method 
    const userInfo = async () => {
        userTB = await account.info();
    }
    let promise = userInfo();

</script>
{#await promise}
    <LoadingPage/>
{:then} 
    <div class="padding-6">
        <div>
            <p class="fs-18 pretendard-bold">내 정보</p>
        </div>

        <div class="mt-20">
            <div class="display-flex align-items gap-20 border-bottom-1">
                <span class="fs-16 pretendard-regular">이 &nbsp;&nbsp;름</span>
                <span class="fs-16 pretendard-regular">{userTB.user_nm}</span>
            </div>
        </div>
        <div class="mt-20">
            <div class="display-flex align-items gap-20 border-bottom-1">
                <span class="fs-16 pretendard-regular">이메일</span>
                <span class="fs-16 pretendard-regular">{userTB.user_email}</span>
            </div>
        </div>
    </div>
{:catch error}
    <ErrorAxiosPage errMsg={error.message}/>
{/await}

<style>
    .border-bottom-1 {border-bottom: 1px solid #b1b1b1; padding-bottom: 8px;}
</style>