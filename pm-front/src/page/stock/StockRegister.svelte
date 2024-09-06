<script lang="ts">
    import SideMenuBar from "../../layout/SideMenuBar.svelte";
    import Header from "../../layout/Header.svelte";
    import ProductListComponent from "../product/ProductListComponent.svelte";
    import ProductHasListComponent from "../product/ProductHasListComponent.svelte";
    import { stockStList } from "../../option/utill";
    import { stock } from "../../option/stock";

    
    // 
    let stockSt:string = "1";
    let stockNo:number;
    let today = new Date();
    let year = today.getFullYear();
    let month = String(today.getMonth() + 1).padStart(2, "0");
    let date = String(today.getDate()).padStart(2, "0");
    let stockDate:string = year + '-' + month + '-' + date;

    
    // 상품 선택 납품일시 전체 
    let selectProduct:any = [];
    let isProdcut:boolean = false;
    const isProductModal = () => {
        isProdcut = true;
    }

    let isProductHas:boolean = false;
    const isProductHasModal = () => {
        isProductHas = true;
    }

    // 등록 버튼
    const addStockButton = () => {
        if(!selectProduct.product_sq){
            alert("상품을 선택 해 주세요")
        }else if(stockNo === 0){
            alert("개수를 입력 해 주세요")
        }else{
            addStock();
        }
    }

    // 등록 RestApi
    const addStock = async () => {
        let data = {
            stock_st : stockSt,
            product_sq : selectProduct.product_sq,
            stock_no : stockNo, 
            stock_dt : stockDate,
        }
        await stock.input(data);
    } 
    

</script>

<SideMenuBar/>

<div class="wrapper">

    <Header/>
    
    <!-- Content -->
    <div class="padding-12">
        <!-- Title -->
        <div class="mt-10 padding-12 box-shadow-primary background-color-white">
            <p class="fs-20 pretendard-bold">재고 등록</p>
            <div class="mt-10 display-flex align-items gap-5">
                <span class="fs-14 prtendard-regular">재고 관리</span>
                <span class="fs-14 prtendard-regular">/</span>
                <span class="fs-14 prtendard-regular">재고 등록</span>
            </div>
        </div>

        <!--  -->
        <div class="mt-20 padding-12 box-shadow-primary background-color-white">
            <!-- 납품, 판매, 회수 여부 -->
            <div>
                <span class="fs-1rem pretendard-bold">구분 여부 <span class="color-tomato">*</span></span>
                <div class="mt-10 display-table border-radius-4">
                    {#each stockStList as data}
                        <div class="display-table-cell border-default">
                            <button type="button" class="{stockSt === data.value ? "button-update":""} fs-1rem pretendard-regular border-none background-none padding-6-12" on:click={()=>{stockSt = data.value}}>{data.label}</button>
                        </div>
                    {/each}
                </div>
            </div>

            <!-- 상품 선택 -->
            <div class="mt-20">
                <span class="fs-1rem pretendard-bold">상품 선택 <span class="color-tomato">*</span></span>
                <div class="mt-10">
                    <div>
                        {#if selectProduct.length === 0}
                            <div>
                                <p class="fs-1rem pretendard-regular mobile-width-100 min-width-250 padding-8-0">상품을 선택 해 주세요</p>
                            </div>
                            <div>
                                {#if stockSt === "1"}
                                    <button type="button" class="fs-1rem pretendard-regular mobile-width-100 min-width-250 background-none border-default border-radius-4 padding-8-12" on:click={isProductModal}>상품 선택</button>
                                {:else}
                                    <button type="button" class="fs-1rem pretendard-regular mobile-width-100 min-width-250 background-none border-default border-radius-4 padding-8-12" on:click={isProductHasModal}>상품 선택</button>
                                {/if}
                            </div>
                        {:else}
                            <div>
                                <p class="fs-1rem pretendard-regular mobile-width-100 min-width-250 padding-8-0">{selectProduct.product_nm}</p>
                            </div>
                            <div>
                                {#if stockSt === "1"}
                                    <button type="button" class="fs-1rem pretendard-regular mobile-width-100 min-width-250 background-none border-default border-radius-4 padding-8-12" on:click={isProductModal}>상품 변경</button>
                                {:else}
                                    <button type="button" class="fs-1rem pretendard-regular mobile-width-100 min-width-250 background-none border-default border-radius-4 padding-8-12" on:click={isProductHasModal}>상품 변경</button>
                                {/if}
                            </div>
                        {/if}
                    </div>
                    <div>
                        {#if selectProduct.length !== 0}
                            <div class="mt-10 display-table">
                                <div class="display-table-row border-b1">
                                    <div class="display-table-cell border-right-b1 padding-6">
                                        <span class="fs-1rem pretendard-regular">지점</span>
                                    </div>
                                    <div class="display-table-cell padding-6">
                                        <span class="fs-1rem pretendard-regular">{selectProduct.branch_office_nm}</span>
                                    </div>
                                </div>
                                <div class="display-table-row border-b1">
                                    <div class="display-table-cell border-right-b1 padding-6">
                                        <span class="fs-1rem pretendard-regular">상품</span>
                                    </div>
                                    <div class="display-table-cell padding-6">
                                        <span class="fs-1rem pretendard-regular">{selectProduct.product_nm}</span>
                                    </div>
                                </div>
                                <div class="display-table-row border-b1">
                                    <div class="display-table-cell border-right-b1 padding-6">
                                        <span class="fs-1rem pretendard-regular">냉장 • 냉동 여부</span>
                                    </div>
                                    <div class="display-table-cell padding-6">
                                        {#if selectProduct.product_st === "1"}
                                            <span class="fs-1rem pretendard-regular">기본</span>
                                        {:else if selectProduct.product_st === "2"}
                                            <span class="fs-1rem pretendard-regular">냉장</span>
                                        {:else if selectProduct.product_st === "3"}
                                            <span class="fs-1rem pretendard-regular">냉동</span>
                                        {/if}
                                    </div>
                                </div>
                                <div class="display-table-row border-b1">
                                    <div class="display-table-cell border-right-b1 padding-6">
                                        <span class="fs-1rem pretendard-regular">단가</span>
                                    </div>
                                    <div class="display-table-cell padding-6">
                                        <span class="fs-1rem pretendard-regular">{selectProduct.product_price}</span>
                                    </div>
                                </div>
                                <div class="display-table-row border-b1">
                                    <div class="display-table-cell border-right-b1 padding-6">
                                        <span class="fs-1rem pretendard-regular">단위</span>
                                    </div>
                                    <div class="display-table-cell padding-6">
                                        <span class="fs-1rem pretendard-regular">{selectProduct.product_weight} {selectProduct.product_weight_dt}</span>
                                    </div>
                                </div>
                                <div class="display-table-row border-b1">
                                    <div class="display-table-cell border-right-b1 padding-6">
                                        <span class="fs-1rem pretendard-regular">수수료</span>
                                    </div>
                                    <div class="display-table-cell padding-6">
                                        <span class="fs-1rem pretendard-regular">{selectProduct.product_commission} %</span>
                                    </div>
                                </div>
                                
                            </div>
                        {/if}
                    </div>
                </div>
            </div>

            <!-- 개수 -->
            <div class="mt-20">
                <span class="fs-1rem pretendard-bold">개수 <span class="color-tomato">*</span></span>
                <div class="mt-10">
                    <input type="number" class="fs-1rem pretendard-regular mobile-width-100 min-width-250 border-default border-radius-4 padding-8-12" bind:value={stockNo} placeholder="개수 입력"/>
                </div>
            </div>

            <!-- 날짜 -->
            <div class="mt-20">
                <span class="fs-1rem pretendard-bold">납품 ⦁ 판매 ⦁ 회수 당일 날짜 <span class="color-tomato">*</span></span>
                <div class="mt-10">
                    <input type="date" class="fs-1rem f-nato mobile-width-100 min-width-250 border-default border-radius-4 padding-8-12" bind:value={stockDate}/>
                </div>
            </div>

            <!-- 등록 버튼 -->
            <div class="mt-30 ta-c">
                <button type="button" class="fs-1rem f-nato button-primary background-none border-default border-radius-4 padding-8-16" on:click={addStockButton}>재고 등록</button>
            </div>

        </div>

    </div>

    <div style="height: 100px;">

    </div>

</div>

<ProductListComponent bind:isProduct={isProdcut} bind:selectProduct={selectProduct} />
<ProductHasListComponent bind:isProductHas={isProductHas} bind:selectProduct={selectProduct} />