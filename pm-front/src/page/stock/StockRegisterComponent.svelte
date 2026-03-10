<script lang="ts">
    import { createEventDispatcher } from "svelte";
    import Modal from "../../component/Modal.svelte";
    import { stock } from "../../option/stock";
    import { noti } from "../../option/store";
    import { priceReplace } from "../../option/utill";

    export let isModal:boolean = false;
    export let choiceData:any = [];
    $:getData = choiceData;

    $:branchOfficeName = getData.branch_office_nm;
    $:productSq = getData.product_sq;
    $:productName = getData.product_nm;
    $:productPrice = getData.product_price;
    $:stockNo = getData.product_stock - getData.product_sell - getData.product_recall;
    $:productWeight = getData.product_weight;
    $:productWeightDt = getData.product_weight_dt;
    $:productCommission = getData.product_commission;
    let stockNumber:number;

    let stockSt:string = "2";
    let stockStList = [
        {label:"판매", value:"2"},
        {label:"회수", value:"3"},
    ]

    let today = new Date();
    let year = today.getFullYear();
    let month = String(today.getMonth() + 1).padStart(2, "0");
    let date = String(today.getDate()).padStart(2, "0");
    let stockDate:string = year + '-' + month + '-' + date;

    // 등록 버튼
    const addStockButton = () => {
        if(!productSq){
            alert("상품을 선택 해 주세요")
        }else if(stockNumber <= 0){
            alert("개수를 입력 해 주세요")
        }else if(stockNumber > stockNo){
            noti.danger("재고수보다 많은 개수를 입력 하셨습니다", 1500)
        }else {
            addStock();
        }
    }
    // 등록 RestApi
    const dispatch = createEventDispatcher();
    const addStock = async () => {
        let data = {
            stock_st : stockSt,
            product_sq : productSq,
            stock_no : stockNumber, 
            stock_dt : stockDate,
        }
        const res = await stock.input(data);
        if(res === 1){
            dispatch("refresh")
            isModal = false;
            noti.success("재고 등록 완료", 1500);
        }
    } 

</script>

<Modal bind:isModal={isModal}>
    <span slot="modal-title" class="fs-1rem pretendard-bold color-white">빠른 재고등록</span>
    <div slot="modal-content">

        <div class="grid grid-template-repeat-2-1 border-default border-radius-4">
            {#each stockStList as data}
                <button type="button" class="{stockSt === data.value ? "button-update":""} fs-1rem pretendard-regular border-none background-none padding-6-12" on:click={()=>{stockSt = data.value}}>{data.label}</button>
            {/each}
        </div>

        <div class="mt-10 border-b1">
            <div class="width-100 grid grid-template-repeat-2-1 border-bottom-b1">
                <span class="fw-b fs-1rem f-nato padding-8">지점</span>
                <span class="fs-1rem f-nato padding-8">{branchOfficeName}</span>
            </div>
            <div class="width-100 grid grid-template-repeat-2-1 border-bottom-b1">
                <span class="fw-b fs-1rem f-nato padding-8">상품</span>
                <span class="fs-1rem f-nato padding-8">{productName}</span>
            </div>
            <div class="width-100 grid grid-template-repeat-2-1 border-bottom-b1">
                <span class="fw-b fs-1rem f-nato padding-8">가격</span>
                <span class="fs-1rem f-nato padding-8">{priceReplace(productPrice)} 원</span>
            </div>
            <div class="width-100 grid grid-template-repeat-2-1 border-bottom-b1">
                <span class="fw-b fs-1rem f-nato padding-8">재고</span>
                <span class="fs-1rem f-nato padding-8">{stockNo} 개</span>
            </div>
            <div class="width-100 grid grid-template-repeat-2-1 border-bottom-b1">
                <span class="fw-b fs-1rem f-nato padding-8">단위</span>
                <span class="fs-1rem f-nato padding-8">{productWeight} {productWeightDt}</span>
            </div>
            <div class="width-100 grid grid-template-repeat-2-1">
                <span class="fw-b fs-1rem f-nato padding-8">수수료</span>
                <span class="fs-1rem f-nato padding-8">{productCommission} %</span>
            </div>
        </div>

        <!-- 개수 -->
        <div class="mt-10">
            <span class="fs-1rem pretendard-bold">개수 <span class="color-tomato">*</span></span>
            <div class="mt-10">
                <input type="number" class="fs-1rem pretendard-regular mobile-width-100 min-width-250 border-default border-radius-4 padding-8-12" bind:value={stockNumber} placeholder="개수 입력"/>
            </div>
        </div>

        <!-- 날짜 -->
        <div class="mt-10">
            <span class="fs-1rem pretendard-bold">납품 ⦁ 판매 ⦁ 회수 당일 날짜 <span class="color-tomato">*</span></span>
            <div class="mt-10">
                <input type="date" class="fs-1rem f-nato mobile-width-100 min-width-250 border-default border-radius-4 padding-8-12" bind:value={stockDate}/>
            </div>
        </div>

        <!-- 등록 버튼 -->
        <div class="mt-20 ta-c">
            <button type="button" class="fs-1rem f-nato fw-b button-primary background-none border-default border-radius-4 padding-8-16" on:click={addStockButton}>재고 등록</button>
        </div>
        

    </div>
</Modal>