<script lang="ts">
    import BranchOfficeListComponent from "../branchoffice/BranchOfficeListComponent.svelte";
    import { createEventDispatcher } from "svelte";
    import Modal from "../../component/Modal.svelte";
    import { product } from "../../option/product";
    import { noti } from "../../option/store";
    import { keepDatas, onlyNumber, weightDatas } from "../../option/utill";


    // 수정
    export let isModal:boolean = false;
    export let sequence:number = 0;

    let branchOfficeSq:number = 0;
    let branchOfficeName:string = "";
    let productName:string = "";
    let productSt:string = "";
    let productPrice:string = "";
    let productCommission:string = "0";
    let productWeight:string = "0";
    let productWeightDt:string = ""
    let productMeasure:string = "";

    // 지점 선택
    let isBranchOffice:boolean = false;
    const isBranchOfficeModal = () => {
        isBranchOffice = true;
    }

    const getProduct = async () => {
        const productInfo = await product.info(sequence);

        branchOfficeSq = productInfo.branch_office_sq;
        branchOfficeName = productInfo.branch_office_nm;
        productName = productInfo.product_nm;
        productSt = productInfo.product_st;
        productPrice = productInfo.product_price;
        productCommission = productInfo.product_commission;
        productWeight = productInfo.product_weight;
        productWeightDt = productInfo.product_weight_dt;
        productMeasure = productInfo.product_measure;
        
    }

    // 수정 버튼 validate check
    const modifyProductButton = () =>{
        let commissionReg = /^[0-9]{1,3}\.?[0-9]{0,5}$/
        if(!branchOfficeSq){
            alert("지점을 선택 해 주세요")
        }else if(!productName){
            alert("상품 명을 입력 해 주세요")
        }else if(!productPrice){
            alert("상품 단가을 입력 해 주세요")
        }else if(!commissionReg.test(productCommission)){
            alert("수수료 소수점은 한개만 표기바람, 숫자만 입력 바람")
        }else if(!productCommission){
            alert("수수료를 입력 해 주세요/n수수료 없을 시 0 표기바람")
        }else if(!productWeight || !productWeightDt){
            alert("단위를 입력, 선택 해 주세요")
        }else{
            modifyProduct();
        }
    }

    // 수정
    const dispatch = createEventDispatcher();
    const modifyProduct = async () => {
        let data = {
            branch_office_sq : branchOfficeSq,
            product_nm : productName,
            product_st : productSt, 
            product_price : productPrice,
            product_commission : productCommission,
            product_weight : productWeight,
            product_weight_dt : productWeightDt,
            product_measure : productMeasure
        }
        const productModify = await product.modify(sequence, data)
        if(productModify === 1){
            isModal = false;
            dispatch("refresh")
            noti.success("상품 수정 완료", 2000)
        }
    }
</script>

<!-- Modify Modal -->
<Modal bind:isModal={isModal}>

    <span slot="modal-title" class="fs-1rem pretendard-bold color-white">상품 수정</span>

    <div slot="modal-content">
        {#await getProduct() then }
            <!-- 지점 선택 -->
            <div>
                <span class="fs-1rem pretendard-bold">지점 선택 <span class="color-tomato">*</span></span>
                <div class="mt-10">
                    {#if !branchOfficeName}
                        <div>
                            <p class="fs-1rem pretendard-regular mobile-width-100 min-width-250 padding-8-0">지점을 선택 해 주세요</p>
                        </div>
                        <div>
                            <button type="button" class="fs-1rem pretendard-regular mobile-width-100 min-width-250 background-none border-default border-radius-4 padding-8-12" on:click={isBranchOfficeModal}>지점 선택</button>
                        </div>
                    {:else}
                        <div>
                            <p class="fs-1rem pretendard-regular mobile-width-100 min-width-250 padding-8-0">{branchOfficeName}</p>
                        </div>
                        <div>
                            <button type="button" class="fs-1rem pretendard-regular mobile-width-100 min-width-250 background-none border-default border-radius-4 padding-8-12" on:click={isBranchOfficeModal}>지점 변경</button>
                        </div>
                    {/if}
                </div>
            </div>

        <!-- 상품 이름 -->
        <div class="mt-20">
            <span class="fs-1rem pretendard-bold">상품명<span class="color-tomato">*</span></span>
            <div class="mt-5">
                <input type="text" class="fs-1rem pretendard-regular width-100 border-default border-radius-4 padding-8-12" bind:value={productName} placeholder="상품 이름 입력"/>
            </div>
        </div>

        <!-- 기본, 냉장, 냉동 여부 -->
        <div class="mt-20">
            <span class="fs-1rem pretendard-bold">냉장, 냉동 여부<span class="color-tomato">*</span></span>
            <div class="mt-5">
                {#each keepDatas as data}
                    <label class="fs-1rem pretendard-regular">
                        <input type="radio" bind:group={productSt} value={data.value} />
                        {data.label}
                    </label>
                {/each}
                
            </div>
        </div>

        <!-- 상품 가격 -->
        <div class="mt-20">
            <span class="fs-1rem pretendard-bold">단가 <span class="color-tomato">*</span></span>
            <div class="mt-5">
                <input type="text" class="fs-1rem pretendard-regular width-100 border-default border-radius-4 padding-8-12" bind:value={productPrice} on:input={()=>{productPrice = onlyNumber(productPrice)}} maxlength="20" placeholder="상품 가격 입력(숫자만)"/>
            </div>
        </div>

        <!-- 수수료 -->
        <div class="mt-20">
            <span class="fs-1rem pretendard-bold">수수료 <span class="color-tomato">*</span></span>
            <div class="mt-5">
                <input type="text" class="fs-1rem pretendard-regular width-100 border-default border-radius-4 padding-8-12" bind:value={productCommission} placeholder="수수료 입력"/>
                <p class="mt-5 fs-14 pretendard-regular color-b1">Ex) 11% -> 11, 11.5% -> 11.5, 없음은 0으로 표기</p>
            </div>
        </div>

        <!-- 상품 무게 -->
        <div class="mt-20">
            <span class="fs-1rem pretendard-bold">상품 단위 <span class="color-tomato">*</span></span>
            <div class="mt-5 flex-to-block align-items gap-5">
                <input type="text" class="mt-5 fs-1rem pretendard-regular width-100 border-default border-radius-4 padding-8-12" bind:value={productWeight} on:input={()=>{productWeight = onlyNumber(productWeight)}} placeholder="무게 입력"/>
                <div class="mt-5 width-100">
                    <select bind:value={productWeightDt} class="fs-1rem pretendard-regular width-100 border-default border-radius-4 padding-8-12">
                        <option class="fs-1rem pretendard-regular" value="">단위 선택</option>
                        {#each weightDatas as data}
                            <option class="fs-1rem pretendard-regular" value={data}>{data}</option>
                        {/each}
                    </select>
                </div>
            </div>
        </div>

        <!-- 등록 버튼 -->
        <div class="mt-30 ta-c">
            <button type="button" class="fs-1rem pretendard-regular button-primary background-none border-default border-radius-4 padding-8-12" on:click={modifyProductButton}>상품 수정</button>
        </div>
        {/await}
    </div>
</Modal>

<!-- 지점 리스트 -->
<BranchOfficeListComponent bind:isBranchOffice={isBranchOffice} bind:branchOfficeSq={branchOfficeSq} bind:branchOfficeName={branchOfficeName}/>

<style>
    .flex-to-block {display: flex;}

    @media all and (max-width: 768px){
        .flex-to-block {display: block;}
    }
</style>