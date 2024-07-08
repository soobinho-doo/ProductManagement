<script lang="ts">
    import { createEventDispatcher } from "svelte";
    import Modal from "../../component/Modal.svelte";
    import BranchOfficeListComponent from "../branchoffice/BranchOfficeListComponent.svelte";
    import { product } from "../../option/product";
    import LoadingPage from "../../loading/LoadingPage.svelte";
    import { link } from "svelte-spa-router";
    import { priceReplace } from "../../option/utill";

    // 지점 선택
    let branchOfficeSq:number = 0;
    let branchOfficeName:string = "";
    let isBranchOffice:boolean = false;
    const isBranchOfficeModal = () => {
        isBranchOffice = true;
    }

    const openBranshOffice = () => {
        cp = 1;
        isBranchOfficeModal();
    }

    export let selectProduct:any = [];

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

    // 지점 모달
    export let isProduct:boolean = false;
    $:if(isProduct){
        branchOfficeName = "";
    }

    // 지점 초이스
    const dispatch = createEventDispatcher();
    const productChoice = (data:any) => {
        isProduct = false;
        selectProduct = data
        dispatch("refresh")
        cp = 1;
        branchOfficeSq = 0;
        branchOfficeName = "";
    }
</script>

<Modal bind:isModal={isProduct}>
    <span slot="modal-title" class="fs-1rem pretendard-bold color-white">상품 리스트</span>
    <div slot="modal-content">
        {#await getProductList()} 
            <LoadingPage/>
        {:then} 
            <div class="display-grid width-100">
                {#if productDatas.length === 0}
                    <div class="mt-10 ta-c">
                        <p class="fs-1rem pretendard-regular">등록 된 상품이 없습니다</p>
                        <a href="/product/register" class="fs-1rem pretendard-regular color-tomato" use:link>상품 등록 하기</a>
                    </div>
                {:else}
                    <div>
                        <button type="button" class="fs-1rem pretendard-regular background-none border-default border-radius-4 padding-8-12 when-480" on:click={openBranshOffice}>
                            {#if branchOfficeName}
                                {branchOfficeName}
                            {:else}
                                전체 지점
                            {/if}
                        </button>
                    </div>
                    <div class="mt-10 display-grid">
                        {#each productDatas as data}
                            <button type="button" class="fs-1rem pretendard-regular button-hover background-none border-none border-bottom-b1 padding-8" on:click={()=>{productChoice(data)}}>{data.branch_office_nm} {data.product_nm} {priceReplace(data.product_price)}원 {data.product_weight}{data.product_weight_dt} 
                            {#if data.product_st === "1"}
                                <span class="fs-1rem pretendard-regular">기본</span>
                            {:else if data.product_st === "2"}
                                <span class="fs-1rem pretendard-regular">냉장</span>
                            {:else if data.product_st === "3"}
                                <span class="fs-1rem pretendard-regular">냉동</span>
                            {/if}</button>
                        {/each}
                    </div>
                    <div class="mt-20 display-flex align-items gap-5">
                        {#if cp !== 1}
                            <div>
                                <button type="button" class="fs-1rem pretendard-regular background-none border-default button-primary padding-4-10" on:click={()=>{cp=cp-1; getProductList()}}>이전</button>
                            </div>
                        {/if}
                        
                        <div>
                            {#each pageDatas as data}
                                {#if data === cp}
                                    <button type="button" class="fs-1rem pretendard-regular background-none button-update padding-4-10">{data}</button>    
                                {:else}
                                    <button type="button" class="fs-1rem pretendard-regular background-none border-default button-primary padding-4-10" on:click={()=>{cp=data; getProductList()}}>{data}</button>
                                {/if}
                            {/each}
                        </div>
    
                        {#if cp !== pageCount }
                            <div>
                                <button type="button" class="fs-1rem pretendard-regular background-none border-default button-primary padding-4-10" on:click={()=>{cp=cp+1; getProductList()}}>다음</button>
                            </div>
                        {/if}
                    </div>
                {/if}
            </div>
        {/await}
    </div>
</Modal>

<BranchOfficeListComponent bind:isBranchOffice={isBranchOffice} bind:branchOfficeSq={branchOfficeSq} bind:branchOfficeName={branchOfficeName} on:refresh={getProductList} />