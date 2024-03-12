<script lang="ts">
    import Header from "../../layout/Header.svelte";
    import SideMenuBar from "../../layout/SideMenuBar.svelte";
    import { onMount } from "svelte";

    import TodaySalesStatus from "./TodaySalesStatus.svelte";

    import YearStatus from "./YearStatus.svelte";
    import MonthStatus from "./MonthStatus.svelte";
    import DayStatus from "./DayStatus.svelte";

    onMount(()=>{
        scrollTo(0,0)
    })

    let menuSelect = 1;
    let menus = [
        { id: 1, name:"일별 판매량", component:DayStatus},
        { id: 2, name:"월별 판매량", component:MonthStatus},
        { id: 3, name:"연도별 판매량", component:YearStatus},
    ]


</script>

<SideMenuBar/>

<div class="wrapper">
    <Header/>
    <div class="layout-padding">
        <!-- Title -->
        <div class="mt-10 padding-12 box-shadow-primary background-color-white">
            <p class="fs-20 pretendard-bold">판매현황</p>
            <div class="mt-10 display-flex align-items gap-5">
                <span class="fs-14 prtendard-regular">홈</span>
                <span class="fs-14 prtendard-regular">/</span>
                <span class="fs-14 prtendard-regular">판매현황</span>
            </div>
        </div>

        <div class="mt-10">
            <TodaySalesStatus/>
        </div>

        <!--  -->
        <div class="mt-10 padding-12 box-shadow-primary background-color-white">
            <!-- 메뉴들 -->
            <div class="display-flex align-items">
                {#each menus as menu}
                    <button type="button" class="{menuSelect === menu.id ? 'button-update' : ''} fs-16 pretendard-bold background-none border-none width-100 padding-12-16 button-primary" on:click={() => { menuSelect = menu.id }}>{menu.name}</button>   
                {/each}
            </div>

            <!-- 메뉴에 해당하는 컴포넌트 -->
            <div class="mypage-content">
                {#each menus as menu}
                    {#if menuSelect === menu.id}
                        <svelte:component this={menu.component}/>
                    {/if}
                {/each}
            </div>
        </div>
        
    </div>

</div>