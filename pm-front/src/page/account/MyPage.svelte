<script lang="ts">
    import Header from "../../layout/Header.svelte";
    import SideMenuBar from "../../layout/SideMenuBar.svelte";

    // Tap Page
    import MyInfo from "./MyInfo.svelte";
    import MyDelete from "./MyDelete.svelte";
    import { auth } from "../../option/auth";

    let menus = [
        { id: 1, name:"내 정보", component:MyInfo},
        { id: 2, name:"회원 탈퇴", component:MyDelete},
    ]
    let menuSelect = 1;

    // Method
    const logout = async () => {
        await auth.logout();
    }

</script>

<SideMenuBar/>

<div class="wrapper">
    <Header/>

    <!-- Content -->
    <div class="padding-12">
        <!-- Title -->
        <div class="mt-10 padding-12 box-shadow-primary background-color-white">
            <p class="fs-20 pretendard-bold">마이페이지</p>
            <div class="mt-10 display-flex align-items gap-5">
                <span class="fs-14 prtendard-regular">홈</span>
                <span class="fs-14 prtendard-regular">/</span>
                <span class="fs-14 prtendard-regular">마이페이지</span>
            </div>
        </div>

        <!--  -->
        <div class="mt-20 padding-12 box-shadow-primary background-color-white">
            <!--  -->
            <div class="mt-20 mypage-layout">
                <!-- 작은 메뉴들 -->
                <div class="mypqge-menu-box">
                    {#each menus as menu}
                        <button type="button" class="{menuSelect === menu.id ? 'menu-btn-slect' : ''} fs-1rem f-nato fw-b background-none border-none width-100 padding-12-16 button-primary border-radius-4" on:click={() => { menuSelect = menu.id }}>{menu.name}</button>   
                    {/each}
                        <button type="button" class="fs-1rem f-nato fw-b background-none border-none width-100 padding-12-16 button-primary border-radius-4" on:click={logout}>로그아웃</button>
                </div>
        
                <!-- 메뉴에 해당하는 컴포넌트 -->
                <div class="mypage-content">
                    {#each menus as menu}
                        {#if menuSelect === menu.id}
                            <svelte:component this={menu.component}/>
                        {:else if menuSelect === menu.id}
                            <svelte:component this={menu.component}/>
                        {/if}
                    {/each}
                </div>
            </div>
        </div>
    </div>

</div>

<style>

    .mypage-layout{display: grid; grid-template-columns: 1fr 3fr; padding: 12px 6px;}
    .mypqge-menu-box {padding-right: 12px;}
    .menu-btn-slect {background-color: #2198d5; color: #FFF;}

    @media all and (max-width: 480px){
        .mypage-layout {display: block;}
        .mypqge-menu-box {padding-right: 0px;}
    }
</style>