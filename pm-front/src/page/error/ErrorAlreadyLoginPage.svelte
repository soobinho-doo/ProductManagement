<script lang="ts">
    import { push } from "svelte-spa-router";

</script>

<div>
    <div class="padding-top-50 display-flex align-items justify-content-center">
        <svg xmlns="http://www.w3.org/2000/svg" width="56" height="56" viewBox="0 0 22 22" fill="#ff4a4a">
            <path d="M22 11L19.56 8.22L19.9 4.54L16.29 3.72L14.4 0.540001L11 2L7.6 0.540001L5.71 3.72L2.1 4.53L2.44 8.21L0 11L2.44 13.78L2.1 17.47L5.71 18.29L7.6 21.47L11 20L14.4 21.46L16.29 18.28L19.9 17.46L19.56 13.78L22 11ZM12 16H10V14H12V16ZM12 12H10V6H12V12Z"/>
        </svg>
    </div>
    <div class="mt-20 ta-c">
        <p class="fs-18 pretendard-regular">이미 로그인이 되어있습니다</p>
    </div>
    <div class=" mt-30 display-flex align-items justify-content-center gap-20">
        <button type="button" class="s-16 pretendard-regular padding-12-16 background-none border-black" on:click={()=> {push("/")}}>Home으로 이동</button>
        <button type="button" class="s-16 pretendard-regular padding-12-16 background-none border-black" on:click={()=> {history.back()}}>뒤로 이동</button>
    </div>
    
</div>

<style>
    .padding-top-50 {padding-top: 50px;}
</style>