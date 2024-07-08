<script lang="ts">
    import Header from "../../layout/Header.svelte";
    import SideMenuBar from "../../layout/SideMenuBar.svelte";
    import BranchOfficeListComponent from "../branchoffice/BranchOfficeListComponent.svelte";
    import ProductModifyComponent from "./ProductModifyComponent.svelte";
    import { onMount } from "svelte";
    import { link, push } from "svelte-spa-router";
    import { priceReplace, mathRound, onlyNumber, keepDatas, weightDatas } from "../../option/utill";
    import { product } from "../../option/product";
    import { auth } from "../../option/auth";
    import { get } from "svelte/store";
    import { noti } from "../../option/store";

    onMount(() => {
        getProductList();
    })

    // 리스트 Get
    let productDatas:any = [];
    let cp:number = 1; // 첫 페이지 번호
    let rowCount:number = 0;
    let keyword:string = "";
    let sp:number; // 시작 페이지
    let ep:number; // 마지막 페이지
    let pageCount:number; // 페이지 개수
    let inCommission:string = "";   // 수수료 포함 총 가격
    let exCommission:string = "";   // 수수료 미포함 총 가격
    let pageDatas:any = []; 
    const getProductList = async () => {
        let data = {
            cp: cp, 
            keyword: keyword, 
            branch_office_nm: branchOfficeName,
        }
        const productList:any = await product.list(data);
            productDatas = productList.list;
            rowCount = productList.count;
            cp = productList.cp;
            sp = productList.sp;
            ep = productList.ep;
            pageCount = productList.pageCount;
            inCommission = productList.inCommission;
            exCommission = productList.exCommission;

            pageDatas = [];
            for(let i=sp; i<=ep; i++){
                pageDatas.push(i);
                pageDatas = pageDatas;
            }

    }

    // 수정
    let isModal:boolean = false;
    let sequence:number;

    let branchOfficeSq:number = 0;
    let branchOfficeName:string = "";

    // 지점 선택
    let isBranchOffice:boolean = false;
    const isBranchOfficeModal = () => {
        isBranchOffice = true;
    }

    // 수정 시 상품 정보 가져오기
    const getProductButton = (sq:number) => {
        isModal = !isModal;
        sequence = sq;
    }


    // 삭제 버튼 validate check
    const deleteProductButton = (sq:number) => {
        if(confirm("삭제 하시겠습니까?\n삭제 후 데이터는 복구 되지 않습니다")){
            deleteProduct(sq);
        }else{
            alert("취소 되었습니다");
        }
    }

    // 삭제
    const deleteProduct = async (sq:number) => {
        const productDel = await product.del(sq);
        if(productDel === 1){
            getProductList();
            noti.success("상품 삭제 완료", 2000)
        }
    }

    // Excel Download
    const downloadProductListToExcel = () => {
        const getId = get(auth).id;
        location.href="/api/product/excel/download?user_id="+getId+"&keyword="+keyword+"&branch_office_nm="+branchOfficeName
    }

</script>

<SideMenuBar/>

<div class="wrapper">
    <Header/>

    <!-- Content -->
    <div class="padding-12">
        <!-- Title -->
        <div class="mt-10 padding-12 box-shadow-primary background-color-white">
            <p class="fs-20 pretendard-bold">상품 리스트</p>
            <div class="mt-10 display-flex align-items gap-5">
                <span class="fs-14 prtendard-regular">상품 관리</span>
                <span class="fs-14 prtendard-regular">/</span>
                <span class="fs-14 prtendard-regular">상품 리스트</span>
            </div>
        </div>

        <!--  -->
        <div class="mt-20 padding-12 box-shadow-primary background-color-white">
            <!-- Search -->
            
            <div class="search-box gap-10">
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
                    <input type="search" class="fs-1rem pretendard-regular border-default border-radius-4 inp-default padding-8-12 when-480" bind:value={keyword} on:input={()=>{cp = 1; getProductList()}} placeholder="상품 명 입력"/>
                    <button type="button" class="border-none background-none padding-4" on:click={()=>{getProductList()}}>
                        <svg xmlns="http://www.w3.org/2000/svg" class="svg-color-change" width="18" height="18" viewBox="0 0 512 512">
                            <path d="M416 208c0 45.9-14.9 88.3-40 122.7L502.6 457.4c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L330.7 376c-34.4 25.2-76.8 40-122.7 40C93.1 416 0 322.9 0 208S93.1 0 208 0S416 93.1 416 208zM208 352a144 144 0 1 0 0-288 144 144 0 1 0 0 288z"/>
                        </svg>
                    </button>
                </div>
            </div>
            

            <div class="mt-20 display-flex align-items justify-content-between">
                <span class="fs-1rem pretendard-regular">등록 상품 {rowCount}</span>
                <div class="display-flex align-items gap-5">
                    <button type="button" class="fs-1rem pretendard-regular button-primary background-none border-default border-radius-4 padding-6-12" on:click={()=>{push("/product/register")}}>상품 등록</button>
                    <button type="button" class="display-flex align-items gap-5 excel-btn background-color-white border-radius-4 padding-4-8" on:click={downloadProductListToExcel}>
                        <svg xmlns="http://www.w3.org/2000/svg" class="excel-svg" width="24" height="22" viewBox="0 0 48 48">
                            <g fill="none" stroke="green" stroke-linecap="round" stroke-width="4">
                                <path stroke-linejoin="round" d="M8 15V6a2 2 0 0 1 2-2h28a2 2 0 0 1 2 2v36a2 2 0 0 1-2 2H10a2 2 0 0 1-2-2v-9"/>
                                <path d="M31 15h3m-6 8h6m-6 8h6"/>
                                <path stroke-linejoin="round" d="M4 15h18v18H4zm6 6l6 6m0-6l-6 6"/>
                            </g>
                        </svg>
                        <span class="fs-1rem pretendard-regular">엑셀</span>
                    </button>
                </div>
            </div>
            <!-- Search End -->

            <!-- Window Content -->
            <div class="mt-10 display-table width-100 when-window">
                <div class="display-table-row border-b1 background-color-custom-orange text-white">
                    
                    <div class="display-table-cell padding-12">
                        <span class="fs-1rem pretendard-bold">지점</span> 
                    </div>
                    <div class="display-table-cell padding-12">
                        <span class="fs-1rem pretendard-bold">진열</span> 
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
                        <span class="fs-1rem pretendard-bold">납품</span> 
                    </div>
                    <div class="display-table-cell ta-c padding-12">
                        <span class="fs-1rem pretendard-bold">판매</span> 
                    </div>
                    <div class="display-table-cell ta-c padding-12">
                        <span class="fs-1rem pretendard-bold">회수</span> 
                    </div>
                    <div class="display-table-cell ta-c padding-12">
                        <span class="fs-1rem pretendard-bold">재고</span> 
                    </div>
                    <div class="display-table-cell ta-c padding-12">
                        <span class="fs-1rem pretendard-bold">수익</span> 
                    </div>
                    <div class="display-table-cell ta-c padding-12">
                        <span class="fs-1rem pretendard-bold">변경 사항</span> 
                    </div>
                </div>
                <!-- Body -->
                {#if productDatas.length !== 0}
                    {#each productDatas as data}
                        <div class="display-table-row border-b1">
                            <div class="display-table-cell padding-12">
                                <span class="fs-1rem pretendard-regular">{data.branch_office_nm}</span> 
                            </div>
                            <div class="display-table-cell padding-12">
                                {#if data.product_st === "1"}
                                    <span class="fs-1rem pretendard-regular">기본</span>
                                {:else if data.product_st === "2"}
                                    <span class="fs-1rem pretendard-regular">냉장</span>
                                {:else if data.product_st === "3"}
                                    <span class="fs-1rem pretendard-regular">냉동</span>
                                {/if}
                            </div>
                            <div class="display-table-cell ta-l padding-12">
                                <span class="fs-1rem pretendard-regular">{data.product_nm}</span> 
                            </div>
                            <div class="display-table-cell ta-r padding-12">
                                <span class="fs-1rem pretendard-regular">{data.product_weight} {data.product_weight_dt}</span>
                            </div>
                            <div class="display-table-cell ta-r padding-12">
                                <span class="fs-1rem pretendard-regular">{priceReplace(data.product_price)} 원</span> 
                            </div>
                            <div class="display-table-cell ta-c padding-12">
                                <span class="fs-1rem pretendard-regular">{data.product_commission} %</span> 
                            </div>
                            <div class="display-table-cell ta-r padding-12">
                                <span class="fs-1rem pretendard-regular">{data.product_stock}</span> 
                            </div>
                            <div class="display-table-cell ta-r padding-12">
                                <span class="fs-1rem pretendard-regular">{data.product_sell}</span> 
                            </div>
                            <div class="display-table-cell ta-r padding-12">
                                <span class="fs-1rem pretendard-regular">{data.product_recall}</span> 
                            </div>
                            <div class="display-table-cell ta-r padding-12">
                                {#if (data.product_stock - data.product_sell - data.product_recall) === 0 }
                                    <span class="fs-1rem pretendard-regular">{data.product_stock - data.product_sell - data.product_recall}</span>
                                {:else}
                                    <span class="fs-1rem pretendard-bold" style="color: red;">{data.product_stock - data.product_sell - data.product_recall}</span>
                                {/if}
                            </div>
                            <div class="display-table-cell ta-r padding-12">
                                <span class="fs-1rem pretendard-regular">{ priceReplace(mathRound((data.product_price * data.product_sell) - (((data.product_price * data.product_sell) * data.product_commission)/100)))} 원</span> 
                            </div>
                            <div class="display-table-cell ta-c padding-12">
                                <button type="button" class="fs-1rem pretendard-regular button-update border-radius-4 padding-4-8" on:click={()=>{getProductButton(data.product_sq)}}>수정</button>
                                <button type="button" class="fs-1rem pretendard-regular button-delete border-radius-4 padding-4-8" on:click={()=>{deleteProductButton(data.product_sq)}}>삭제</button>
                            </div>
                        </div>
                    {/each}
                {/if}
            </div>

            {#if productDatas.length === 0}
                <div class="mt-30 ta-c when-window">
                    <p class="fs-1rem pretendard-regular">등록 된 상품이 없습니다</p>
                    <a href="/product/register" class="fs-1rem pretendard-regular color-tomato" use:link>상품 등록 하기</a>
                </div>
            {/if}
            <!-- Window End -->

            <!-- Mobile Start -->
            <div class="mt-10 when-mobile">
                {#if productDatas.length === 0}
                    <div class="mt-30 ta-c">
                        <p class="fs-1rem pretendard-regular">등록 된 상품이 없습니다</p>
                        <a href="/product/register" class="fs-1rem pretendard-regular color-tomato" use:link>상품 등록 하기</a>
                    </div>
                {:else}
                    {#each productDatas as data}
                        <div class="border-b1 padding-12">
                            <div class="display-flex align-items justify-content-between">
                                <span class="fs-1rem pretendard-regular">{data.branch_office_nm}</span>
                                <span class="fs-1rem pretendard-regular">{data.product_nm}</span> 
                            </div>
                            <div class="mt-10 display-flex align-items justify-content-between">
                                <div class="display-flex align-items gap-10">
                                    {#if data.product_st === "1"}
                                        <span class="fs-1rem pretendard-regular">기본</span>
                                    {:else if data.product_st === "2"}
                                        <span class="fs-1rem pretendard-regular">냉장</span>
                                    {:else if data.product_st === "3"}
                                        <span class="fs-1rem pretendard-regular">냉동</span>
                                    {/if}
                                    <span class="fs-1rem pretendard-regular">{data.product_weight} {data.product_weight_dt}</span>
                                    <span class="fs-1rem pretendard-regular">{data.product_commission} %</span>
                                </div>
                                <div>
                                    <span class="fs-1rem pretendard-regular">{priceReplace(data.product_price)} 원</span> 
                                </div>  
                            </div>
                            
                            <div class="mt-10 display-flex align-items justify-content-between">
                                <div class="display-flex align-items gap-10">
                                    <span class="fs-1rem pretendard-regular">{data.product_stock}</span>
                                    <span class="fs-1rem pretendard-regular">{data.product_sell}</span>
                                    <span class="fs-1rem pretendard-regular">{data.product_recall}</span>
                                    {#if (data.product_stock - data.product_sell - data.product_recall) === 0 }
                                        <span class="fs-1rem pretendard-regular">{data.product_stock - data.product_sell - data.product_recall}</span>
                                    {:else}
                                        <span class="fs-1rem pretendard-bold" style="color: red;">{data.product_stock - data.product_sell - data.product_recall}</span>
                                    {/if}
                                </div>
                                <div>
                                    <span class="fs-1rem pretendard-regular">{ priceReplace( mathRound((data.product_price * data.product_sell) - (((data.product_price * data.product_sell) * data.product_commission)/100)))} 원</span>
                                </div>
                            </div>
                            
                            <div class="mt-20 ta-r">
                                <button type="button" class="fs-1rem pretendard-regular button-update border-radius-4 padding-4-8" on:click={()=>{getProductButton(data.product_sq)}}>수정</button>
                                <button type="button" class="fs-1rem pretendard-regular button-delete border-radius-4 padding-4-8" on:click={()=>{deleteProductButton(data.product_sq)}}>삭제</button>
                            </div>
                        </div>
                    {/each}
                {/if}
            </div>
            <!-- Mobile End -->

            <div class="mt-20 display-flex align-items justify-content-right gap-10">
                <span class="fs-1rem pretendard-regular">판매 총액 :  <span class="fs-1rem pretendard-regular color-custom-blue">{priceReplace(exCommission)}</span> 원</span>
                <span class="fs-1rem pretendard-regular">|</span>
                <span class="fs-1rem pretendard-regular">수익 총액 :  <span class="fs-1rem pretendard-regular color-custom-blue">{priceReplace(inCommission)}</span> 원</span>
            </div>

            <!-- 페이징 -->
            {#if rowCount >= 10}
                <div class="mt-30 display-flex align-items gap-5">
                    {#if cp !== 1}
                        <div>
                            <button type="button" class="fs-1rem pretendard-regular background-none border-default button-primary padding-4-10" on:click={()=>{cp = cp-1; getProductList()}}>이전</button>
                        </div>
                    {/if}
                    <div>
                        {#each pageDatas as data}
                            {#if data === cp}
                                <button type="button" class="fs-1rem pretendard-regular background-none button-update padding-4-10">{data}</button>    
                            {:else}
                                <button type="button" class="fs-1rem pretendard-regular background-none border-default button-primary padding-4-10" on:click={()=>{cp= data;getProductList()}}>{data}</button>
                            {/if}
                        {/each}
                    </div>
                    {#if cp !== pageCount }
                        <div>
                            <button type="button" class="fs-1rem pretendard-regular background-none border-default button-primary padding-4-10" on:click={()=>{cp = cp+1; getProductList()}}>다음</button>
                        </div>
                    {/if}
                </div>
            {/if}
            <!--  -->

        </div>

    </div>
    <div style="height: 100px;">

    </div>

</div>

<!-- 수정 -->
<ProductModifyComponent bind:isModal={isModal} bind:sequence={sequence} on:refresh={()=>{cp=1; getProductList()}}/>

<!-- 지점 리스트 -->
<BranchOfficeListComponent bind:isBranchOffice={isBranchOffice} bind:branchOfficeSq={branchOfficeSq} bind:branchOfficeName={branchOfficeName} on:refresh={()=>{cp=1; getProductList()}}/>

<style>
    .search-box {display: flex; align-items: center; gap: 10px;}
    .excel-svg {vertical-align: middle;}
    .excel-btn {border: 1px solid green; transition: background, 0.3s;}
    .excel-btn > span {color: green;}
    .excel-btn:hover {background-color: rgb(6, 129, 6);}
    .excel-btn:hover > svg > g {stroke: #FFF;}
    .excel-btn:hover > span {color: #FFF;}

    .when-mobile {display: none;}

    @media all and (max-width: 768px){
        .search-box {width: 100%; display: block;}

        .when-480 {width: 100%;}

        .when-window {display: none;}
        .when-mobile {display: block;}
    }
</style>