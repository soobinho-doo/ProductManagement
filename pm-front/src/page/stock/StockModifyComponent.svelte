<script lang="ts">
    import { createEventDispatcher } from "svelte";
    import Modal from "../../component/Modal.svelte";
    import ProductListComponent from "../product/ProductListComponent.svelte";
    import { stock } from "../../option/stock";
    import { noti } from "../../option/store";
    import { stockStList } from "../../option/utill";
    import { product } from "../../option/product";

    export let isModal:boolean = false;
    export let sequence:number = 0;

    let selectProductSq:number = 0;
    let stockState:string = "1";
    let stockNo:number = 0;
    let stockDate:string = "";

    export let getData = {
        getStockSq: 0,
        getStockSt: "",
        getStockNo: "",
        getProductSq: "",
        getStockDt: "",
    }

    // Get Stock
    const getStock = async () => {
        const stockInfo = await stock.info(sequence);
        stockState = stockInfo.stock_st;
        selectProductSq = stockInfo.product_sq;
        stockNo = stockInfo.stock_no;
        stockDate = stockInfo.stock_dt;
        getSelectProduct();
    }

    // 상품 선택
    let selectProduct:any = [];
    let isProdcut:boolean = false;
    const isProductModal = () => {
        isProdcut = true;
    }

    //
    const getSelectProduct = async () => {
        const productInfo = await product.info(selectProductSq)
        selectProduct = productInfo;   
    }

    // 수정 버튼
    const stockModifyButton = () => {
        if(!selectProduct.product_sq){
            alert("상품을 선택 해 주세요")
        }else if(stockNo === 0){
            alert("개수를 입력 해 주세요")
        }else{
            modifyStock();
        }
        
    }

    // 수정 RestApi
    const dispatch = createEventDispatcher();
    const modifyStock = async () => {
        let data = {
            before_stock_st : getData.getStockSt,
            before_product_sq : getData.getProductSq,
            before_stock_no : getData.getStockNo,
            before_stock_dt : getData.getStockDt,
            stock_sq : getData.getStockSq,
            stock_st : stockState,
            product_sq : selectProduct.product_sq,
            stock_no : stockNo, 
            stock_dt : stockDate,
        }
        const stockModify = await stock.modify(getData.getStockSq, data);
        if(stockModify === 1){
            isModal = false;
            dispatch("refresh")
            noti.success("재고 수정 완료", 2000);
        }
    } 

</script>

<Modal bind:isModal={isModal}>
    <span slot="modal-title" class="fs-1rem pretendard-bold color-white">재고 수정</span>

    <div slot="modal-content">
        {#await getStock() then }
            <!-- 납품, 판매, 회수 여부 -->
            <div>
                <span class="fs-1rem pretendard-bold">납품 • 판매 • 회수 여부 <span class="color-tomato">*</span></span>
                <div class="mt-10">
                    {#each stockStList as data}
                        <label class="fs-1rem pretendard-regular padding-right-12">
                            <input type="radio" bind:group={stockState} value={data.value} />
                            {data.label}
                        </label>
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
                                <button type="button" class="fs-1rem pretendard-regular mobile-width-100 min-width-250 background-none border-default border-radius-4 padding-8-12" on:click={isProductModal}>상품 선택</button>
                            </div>
                        {:else}
                            <div>
                                <p class="fs-1rem pretendard-regular mobile-width-100 min-width-250 padding-8-0">{selectProduct.product_nm}</p>
                            </div>
                            <div>
                                <button type="button" class="fs-1rem pretendard-regular mobile-width-100 min-width-250 background-none border-default border-radius-4 padding-8-12" on:click={isProductModal}>상품 변경</button>
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
                                            <span class="fs-1rem pretendard-regular">기본 제품</span>
                                        {:else if selectProduct.product_st === "2"}
                                            <span class="fs-1rem pretendard-regular">냉장 제품</span>
                                        {:else if selectProduct.product_st === "3"}
                                            <span class="fs-1rem pretendard-regular">냉동 제품</span>
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
                    <input type="number" class="fs-1rem pretendard-regular border-default border-radius-4 padding-8-12" bind:value={stockNo}/>
                </div>
            </div>

            <!-- 날짜 -->
            <div class="mt-20">
                <span class="fs-1rem pretendard-bold">납품, 판매, 회수 당일 날짜 <span class="color-tomato">*</span></span>
                <div class="mt-10">
                    <input type="date" class="fs-1rem pretendard-regular border-default border-radius-4 padding-8-12" bind:value={stockDate}/>
                </div>
            </div>

            <!-- 수정 버튼 -->
            <div class="mt-30 ta-c">
                <button type="button" class="fs-1rem pretendard-regular button-primary background-none border-default border-radius-4 padding-8-12" on:click={stockModifyButton}>재고 수정</button>
            </div>
        {/await}
    </div>
</Modal>

<!--  -->
<ProductListComponent bind:isProduct={isProdcut} bind:selectProduct={selectProduct} />