<script lang="ts">
    import { link } from "svelte-spa-router";
    import Header from "../../layout/Header.svelte";
    import SideMenuBar from "../../layout/SideMenuBar.svelte";
    import ChangeTheme from "./ChangeTheme.svelte";

    // 변수


    // Method 

 
</script>

<SideMenuBar/>

<div class="wrapper">
    <Header/>
    <div class="layout-480 padding-12">
        <!-- Title -->
        <div class="mt-10 padding-12 box-shadow-primary background-color-white">
            <p class="fs-20 pretendard-bold">설정</p>
            <div class="mt-10 display-flex align-items gap-5">
                <span class="fs-14 prtendard-regular">홈</span>
                <span class="fs-14 prtendard-regular">/</span>
                <span class="fs-14 prtendard-regular">설정</span>
            </div>
        </div>
    
        <div class="mt-20 padding-12 box-shadow-primary background-color-white">
    
            <!-- 테마 변경 Setting -->
            <div class="display-flex align-items justify-content-between border-bottom-1">
                <div>
                    <sapn class="fs-16 pretendard-bold">테마 변경</sapn>
                </div>
                <div>
                    <ChangeTheme/>
                </div>
            </div>
            
        </div>
    </div>
    
    
</div>

<style>
    .border-bottom-1 {border-bottom: 1px solid #b1b1b1; padding-bottom: 8px;}
</style>