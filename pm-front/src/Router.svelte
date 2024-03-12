<script lang="ts">
    import Router from "svelte-spa-router";
    import axios from "axios";
    import { isConnection } from "./option/store";

    let routes:any;

    //
    let userTB = []
    const isLogin = async () => {
        await axios.get("/api/user").then((res) => {
            userTB = res.data;
            if(userTB.user_auth === "ROLE_A"){
                routes = {
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
            }else{
                routes = {
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
            }
        }).catch();
    }
    
    // 비로그인 시 
    if(!$isConnection){
        routes = {
            "/":LoginPage,
            "/login":LoginPage,
            "/register":RegisterPage,
            "/forgot/id":ForgotIdPage,
            "/forgot/pass":ForgotPassPage,
            "*":Error404Page,
        }
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

</script>

{#if $isConnection}
    {#await isLogin() then }
        <Router routes={routes} restoreScrollState={true}/>
    {/await}
{:else}
    <Router routes={routes} restoreScrollState={true}/>
{/if}