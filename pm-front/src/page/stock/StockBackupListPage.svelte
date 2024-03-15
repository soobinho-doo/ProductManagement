<script lang="ts">
    import SideMenuBar from "../../layout/SideMenuBar.svelte";
    import Header from "../../layout/Header.svelte";
    import { onMount } from "svelte";
    import { priceReplace, stockStDatas } from "../../option/utill";
    import { get } from "svelte/store";
    import { auth } from "../../option/auth";
    import { postApi } from "../../option/api";
    import BranchOfficeListComponent from "../branchoffice/BranchOfficeListComponent.svelte";

    onMount(() => {
        getStockBackupList(cp);
    })

    // 리스트 Get
    let stockDatas:any = [];
    let stockSt:string = "";
    let today = new Date();
    let year = today.getFullYear();
    let month = String(today.getMonth() + 1).padStart(2, "0");
    let date = String(today.getDate()).padStart(2, "0");
    let oneMonthAgo = new Date(today.setMonth(today.getMonth() - 1));
    let oneMonth = String(oneMonthAgo.getMonth() + 1).padStart(2, "0");
    let oneDay = String(oneMonthAgo.getDate()).padStart(2, "0");
    let startDt = oneMonthAgo.getFullYear() + '-'+oneMonth + '-'+oneDay;
    let endDt = year + '-' + month + '-' + date;
    let cp:number; // 첫 페이지 번호
    let rowCount:number = 0;
    let keyword:string = "";
    let sp:number; // 시작 페이지
    let ep:number; // 마지막 페이지
    let pageCount:number; // 페이지 개수
    let pageDatas:any = []; 
    const getStockBackupList = async (cpNum:any) => {
        if(!cpNum){
            cpNum = 1;
        }
        let data = {
            stock_st:stockSt,
            start_dt:startDt,
            end_dt:endDt,
            cp: cpNum, 
            keyword:keyword,
            branch_office_nm:branchOfficeName,
        }
        
        const accessToken = get(auth).Authorization;
        try{
            const options = {
                path:'/api/stock-backup/paging',
                data: data,
                accessToken : accessToken,
            }
            const stockBackupList = await postApi(options)
            stockDatas = stockBackupList.list;
            rowCount = stockBackupList.count;
            cp = stockBackupList.cp;
            sp = stockBackupList.sp;
            ep = stockBackupList.ep;
            pageCount = stockBackupList.pageCount;

            pageDatas = [];
            for(let i=sp; i<=ep; i++){
                pageDatas.push(i);
                pageDatas = pageDatas;
            }
            
        }catch(err){
            throw err
        }
    }

    // 지점 선택
    let branchOfficeSq:number = 0;
    let branchOfficeName:string = "";
    let isBranchOffice:boolean = false;
    const isBranchOfficeModal = () => {
        isBranchOffice = true;
    }


</script>

<SideMenuBar/>

<div class="wrapper">

    <Header/>
    
    <!-- Content -->
    <div class="padding-12">
        <!-- Title -->
        <div class="mt-10 padding-12 box-shadow-primary background-color-white">
            <p class="fs-20 pretendard-bold">재고 백업 리스트</p>
            <div class="mt-10 display-flex align-items gap-5">
                <span class="fs-14 prtendard-regular">재고 관리</span>
                <span class="fs-14 prtendard-regular">/</span>
                <span class="fs-14 prtendard-regular">재고 백업 리스트</span>
            </div>
        </div>

        <!--  -->
        <div class="mt-20 padding-12 box-shadow-primary background-color-white">
            <!-- 검색 -->
            <div class="search-box">
                <!--  -->
                <div class="mt-5 display-flex align-items">
                    {#each stockStDatas as data}
                        <button class="{stockSt === data.value ? 'button-update' : ''} fs-1rem pretendard-regular button-primary background-none border-none padding-6-12 when-480" on:click={()=>{stockSt = data.value; cp = 1; getStockBackupList(cp);}}>{data.name}</button>
                    {/each}
                </div>
                <!--  -->
                <div class="mt-5 display-flex align-items gap-10">
                    <input type="date" class="fs-1rem pretendard-regular border-default border-radius-4 padding-6 when-480" bind:value={startDt} on:change={()=>{startDt = startDt; getStockBackupList(cp);}}/>
                    <span class="fs-1rem pretendard-regular">~</span>
                    <input type="date" class="fs-1rem pretendard-regular border-default border-radius-4 padding-6 when-480" bind:value={endDt} on:change={()=>{endDt = endDt; getStockBackupList(cp);}}/>
                </div>
            </div>
            <!--  -->
            <div class="search-box">
                <div class="mt-10">
                    <button type="button" class="fs-1rem pretendard-regular background-none border-default border-radius-4 padding-8-12 when-480" on:click={isBranchOfficeModal}>
                        {#if branchOfficeName}
                            {branchOfficeName}
                        {:else}
                            전체 지점
                        {/if}
                    </button>
                </div>
                <div class="mt-10 display-flex align-items">
                    <input type="search" class="fs-16 pretendard-regular border-default border-radius-4 inp-default padding-8 width-100" bind:value={keyword} on:input={()=>{keyword = keyword; cp = 1; getStockBackupList(cp);}} placeholder="상품 명 입력"/>
                    <button type="button" class="border-none background-none padding-4" on:click={()=>{getStockBackupList(cp);}}>
                        <svg xmlns="http://www.w3.org/2000/svg" class="svg-color-change" width="18" height="18" viewBox="0 0 512 512">
                            <path d="M416 208c0 45.9-14.9 88.3-40 122.7L502.6 457.4c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L330.7 376c-34.4 25.2-76.8 40-122.7 40C93.1 416 0 322.9 0 208S93.1 0 208 0S416 93.1 416 208zM208 352a144 144 0 1 0 0-288 144 144 0 1 0 0 288z"/>
                        </svg>
                    </button>
                </div>
            </div>
            
            
            <!--  -->
            <div class="mt-20">
                <p class="fs-1rem pretendard-regular">등록 리스트 {rowCount}</p>
            </div>

            <!-- Content -->
            <div class="mt-10 display-table width-100">
                <div class="display-table-row border-b1 background-color-custom-orange">
                    <div class="display-table-cell ta-c padding-12">
                        <span class="fs-1rem pretendard-bold">목적</span> 
                    </div>
                    <div class="display-table-cell ta-c padding-12">
                        <span class="fs-1rem pretendard-bold">지점</span> 
                    </div>
                    <div class="display-table-cell ta-c padding-12">
                        <span class="fs-1rem pretendard-bold">상품</span> 
                    </div>
                    <div class="display-table-cell ta-c padding-12">
                        <span class="fs-1rem pretendard-bold">단위</span> 
                    </div>
                    <div class="display-table-cell ta-c padding-12">
                        <span class="fs-1rem pretendard-bold">단가</span> 
                    </div>
                    <div class="display-table-cell ta-c padding-12">
                        <span class="fs-1rem pretendard-bold">수수료</span> 
                    </div>
                    <div class="display-table-cell ta-c padding-12">
                        <span class="fs-1rem pretendard-bold">진열</span> 
                    </div>
                    <div class="display-table-cell ta-c padding-12">
                        <span class="fs-1rem pretendard-bold">개수</span> 
                    </div>
                    <div class="display-table-cell ta-c padding-12">
                        <span class="fs-1rem pretendard-bold">날짜</span> 
                    </div>
                </div>
                <!--  -->
                {#each stockDatas as data}
                    <div class="display-table-row border-b1">
                        
                        {#if data.stock_st === "1"}
                            <div class="display-table-cell ta-c padding-12" style="background-color: #c6dfec;">
                                <span class="fs-1rem pretendard-bold">납품</span>
                            </div>
                                
                            {:else if  data.stock_st === "2"}
                            <div class="display-table-cell ta-c padding-12" style="background-color: #ffb6a9;">
                                <span class="fs-1rem pretendard-bold">판매</span>
                            </div>
                                
                            {:else if  data.stock_st === "3"}
                            <div class="display-table-cell ta-c padding-12" style="background-color: #caffca;">
                                <span class="fs-1rem pretendard-bold">회수</span>
                            </div>
                        {/if}
                        
                        <div class="display-table-cell padding-12">
                            <span class="fs-1rem pretendard-regular">{data.branch_office_nm}</span> 
                        </div>
                        <div class="display-table-cell padding-12">
                            <span class="fs-1rem pretendard-regular">{data.product_nm}</span> 
                        </div>
                        <div class="display-table-cell ta-r padding-12">
                            <span class="fs-1rem pretendard-regular">{data.product_weight} {data.product_weight_dt}</span> 
                        </div>
                        <div class="display-table-cell ta-r padding-12">
                            <span class="fs-1rem pretendard-regular">{priceReplace(data.product_price)}</span> 
                        </div>
                        <div class="display-table-cell ta-c padding-12">
                            <span class="fs-1rem pretendard-regular">{data.product_commission} %</span> 
                        </div>
                        <div class="display-table-cell ta-c padding-12">
                            {#if data.product_st === "1"}
                                <span class="fs-1rem pretendard-regular">기본</span>
                            {:else if data.product_st === "2"}
                                <span class="fs-1rem pretendard-regular">냉장</span>
                            {:else if data.product_st === "3"}
                                <span class="fs-1rem pretendard-regular">냉동</span>
                            {/if} 
                        </div>
                        <div class="display-table-cell ta-r padding-12">
                            <span class="fs-1rem pretendard-regular">{data.stock_no}</span> 
                        </div>
                        <div class="display-table-cell ta-c padding-12">
                            <span class="fs-1rem pretendard-regular">{data.stock_dt}</span> 
                        </div>
                    </div>                    
                {/each}
            </div>

             <!-- 페이징 -->
            {#if rowCount >= 10}
            <div class="mt-30 display-flex align-items gap-5">
                {#if cp !== 1}
                    <div>
                        <button type="button" class="fs-1rem pretendard-regular background-none border-default button-primary padding-4-10" on:click={()=>{getStockBackupList(cp-1)}}>이전</button>
                    </div>
                {/if}
                
                <div>
                    {#each pageDatas as data}
                        {#if data === cp}
                            <button type="button" class="fs-1rem pretendard-regular background-none button-update padding-4-10">{data}</button>    
                        {:else}
                            <button type="button" class="fs-1rem pretendard-regular background-none border-default button-primary padding-4-10" on:click={()=>{getStockBackupList(data)}}>{data}</button>
                        {/if}
                    {/each}
                </div>

                {#if cp !== pageCount }
                    <div>
                        <button type="button" class="fs-1rem pretendard-regular background-none border-default button-primary padding-4-10" on:click={()=>{getStockBackupList(cp+1)}}>다음</button>
                    </div>
                {/if}
            </div>
            {/if}

        </div>

    </div>

    <div style="height: 100px;">

    </div>

</div>

<!-- 지점 리스트 -->
<BranchOfficeListComponent bind:isBranchOffice={isBranchOffice} bind:branchOfficeSq={branchOfficeSq} bind:branchOfficeName={branchOfficeName} on:refresh={()=>{getStockBackupList(1)}}/>

<style>
    .search-box {display: flex; align-items: center; gap: 10px;}

    @media all and (max-width: 480px){
        .search-box {display: block;}

        .when-480 {width: 100%;}
    }
</style>