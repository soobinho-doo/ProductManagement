<script lang="ts">
    import { push } from "svelte-spa-router";


</script>

<div>
    <div class="padding-top-50 display-flex align-items justify-content-center">
        <svg xmlns="http://www.w3.org/2000/svg" width="56" height="56" viewBox="0 0 24 24" fill="#FFC700">
            <path d="M13 14H11V9H13M13 18H11V16H13M1 21H23L12 2L1 21Z"/>
        </svg>
    </div>
    <div class="mt-20 ta-c">
        <p class="fs-18 pretendard-regular">접근 하실 수 없는 페이지 입니다</p>
    </div>
    <div class=" mt-30 display-flex align-items justify-content-center gap-20">
        <button type="button" class="s-16 pretendard-regular padding-12-16 background-none border-black" on:click={()=> {push("/")}}>Home으로 이동</button>
        <button type="button" class="s-16 pretendard-regular padding-12-16 background-none border-black" on:click={()=> {history.back()}}>뒤로 이동</button>
    </div>
    
</div>

<style>
    .padding-top-50 {padding-top: 50px;}
</style>