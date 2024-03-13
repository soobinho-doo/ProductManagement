<script lang="ts">
    import Router from "svelte-spa-router";
    import { auth, isLogin, isRefresh } from "./option/auth";
    import { get } from "svelte/store";

    onMount(() => {
        const refreshInterval = setTimeout(() => {
            if($isRefresh){
                auth.refresh();
            }else{
                clearInterval(refreshInterval);
            }
        }, 1000 * 60 * 29)
    })

    let role = get(auth).role;

    // 로그인 시 Auth = USER
    let routes:any = {
        "/":MainPage,
        "/login":ErrorAlreadyLoginPage,
        "/register":ErrorAlreadyLoginPage,
        "/mypage":MyPage,
        "/menu":MenuPage,
        "/setting":SettingPage,
        "/branchoffice/register":BranchOfficeRegister,
        "/branchoffice/list":BranchOfficeListPage,
        "/product/register":ProductRegister,
        "/product/list":ProductListPage,
        "/stock/register":StockRegister,
        "/stock/list":StockListPage,
        "/stock/backup/list":StockBackupListPage,
        "/salesstatus":SalesStatusPage,
        "*":Error404Page,
    }

    // 로그인 시 Auth = ADMIN 
    let adminRoutes:any = {
        "/":MainPage,
        "/login":ErrorAlreadyLoginPage,
        "/register":ErrorAlreadyLoginPage,
        "/mypage":MyPage,
        "/menu":MenuPage,
        "/setting":SettingPage,
        "/branchoffice/register":BranchOfficeRegister,
        "/branchoffice/list":BranchOfficeListPage,
        "/product/register":ProductRegister,
        "/product/list":ProductListPage,
        "/stock/register":StockRegister,
        "/stock/list":StockListPage,
        "/stock/backup/list":StockBackupListPage,
        "/salesstatus":SalesStatusPage,
        "*":Error404Page,
    }
    
    // 비로그인 시 
    let noRoutes = {
        "/":LoginPage,
        "/login":LoginPage,
        "/register":RegisterPage,
        "/forgot/id":ForgotIdPage,
        "/forgot/pass":ForgotPassPage,
        "*":Error404Page,
    }

    // Page
    import MainPage from "./page/MainPage.svelte";

    import LoginPage from "./page/join/LoginPage.svelte";
    import RegisterPage from "./page/join/RegisterPage.svelte";
    import MyPage from "./page/account/MyPage.svelte";

    import SettingPage from "./page/setting/SettingPage.svelte";
    import MenuPage from "./page/menu/MenuPage.svelte";

    import ForgotIdPage from "./page/forgot/ForgotIdPage.svelte";
    import ForgotPassPage from "./page/forgot/ForgotPassPage.svelte";

    import Error404Page from "./page/error/Error404Page.svelte";
    import ErrorAlreadyLoginPage from "./page/error/Error404Page.svelte";
    import ErrorNeedLoginPage from "./page/error/ErrorNeedLoginPage.svelte";

    // 지점 관리
    import BranchOfficeRegister from "./page/branchoffice/BranchOfficeRegister.svelte";
    import BranchOfficeListPage from "./page/branchoffice/BranchOfficeListPage.svelte";

    // 상품 관리
    import ProductRegister from "./page/product/ProductRegister.svelte";
    import ProductListPage from "./page/product/ProductListPage.svelte";

    // 재고 관리
    import StockRegister from "./page/stock/StockRegister.svelte";
    import StockListPage from "./page/stock/StockListPage.svelte";
    import StockBackupListPage from "./page/stock/StockBackupListPage.svelte";

    // 현황 관련
    import SalesStatusPage from "./page/salesstatus/SalesStatusPage.svelte";
    import { onMount } from "svelte";

</script>

{#await auth.refresh() then}
    {#if $isLogin}
        {#if role === "ROLE_ADMIN"}
            <Router routes={adminRoutes} restoreScrollState={true}/>
        {:else if role === "ROLE_USER"}
            <Router routes={routes} restoreScrollState={true}/>
        {/if} 
    {:else}
        <Router routes={noRoutes} restoreScrollState={true}/>
    {/if}
{/await}