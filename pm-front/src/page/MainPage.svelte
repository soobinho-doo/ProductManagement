<script lang="ts">
    import Header from "../layout/Header.svelte";
    import SideMenuBar from "../layout/SideMenuBar.svelte";
    import { onMount } from "svelte";

    //
    import ProductHasStock from "./dashboard/ProductHasStock.svelte";
    
    onMount(()=>{
        scrollTo(0,0)
    })

</script>

<SideMenuBar/>

<div class="wrapper">
    <Header/>
    <div class="layout-padding">
        <!-- Title -->
        <div class="mt-10 padding-12 box-shadow-primary background-color-white">
            <p class="fs-20 pretendard-bold">대쉬보드</p>
            <div class="mt-10 display-flex align-items gap-5">
                <span class="fs-14 prtendard-regular">홈</span>
                <span class="fs-14 prtendard-regular">/</span>
                <span class="fs-14 prtendard-regular">대쉬보드</span>
            </div>
        </div>

        <!--  -->
        <div class="mt-10">
            <ProductHasStock/>
        </div>
        
    </div>

    
</div>