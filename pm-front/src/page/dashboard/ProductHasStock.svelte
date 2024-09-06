<script lang="ts">
    import { onMount } from "svelte";
    import { priceReplace } from "../../option/utill";
    import { push } from "svelte-spa-router";
    import { dashBaord } from "../../option/dashboard";

    onMount( async ()=>{
        stockList();
    })

    let branchNames:any = [];
    let productNames:any = [];
    let branchName:string = "";
    let productSq:number;
    let productHasStockList:any = [];
    const stockList = async () => {
        const res = await dashBaord.stockList(branchName, productSq);
        branchNames = res.branchNames;
        productNames = res.productNames;
        productHasStockList = res.stockProducts;
    }

</script>

<div class="padding-12 box-shadow-primary background-color-white">
    <div class="display-flex align-items justify-content-between">
        <span class="fs-18 pretendard-bold">현 재고 상품리스트</span>
        <button type="button" class="background-none border-none" on:click={()=>{push("/product/list")}}>
            <svg xmlns="http://www.w3.org/2000/svg" class="svg-color-change" width="24" height="24" viewBox="0 0 24 24">
                <path d="M11 13H5v-2h6V5h2v6h6v2h-6v6h-2z"/>
            </svg>
        </button>
    </div>

    <!-- Search -->
    <div class="mt-10">
        <select class="fs-1rem f-nato border-b1 border-radius-4 padding-4-12" bind:value={branchName} on:change={stockList}>
            <option class="fs-1rem f-nato" value="">전체지점</option>
            {#each branchNames as data}
                <option class="fs-1rem f-nato" value="{data.branch_office_nm}">{data.branch_office_nm}</option>
            {/each}
        </select>
        <select class="fs-1rem f-nato border-b1 border-radius-4 padding-4-12" bind:value={productSq} on:change={stockList}>
            <option class="fs-1rem f-nato" value="">전체상품</option>
            {#each productNames as data}
                <option class="fs-1rem f-nato" value="{data.product_sq}">{data.product_nm} {priceReplace(data.product_price)} 원 {data.product_weight}{data.product_weight_dt}</option>
            {/each}
        </select>
    </div>
    
    <div class="mt-10 list-box gap-10">
        {#if productHasStockList.length === 0}
            <div>
                <p class="fs-18 pretendard-regular display-flex align-items gap-5">재고가 없습니다</p>
            </div>
        {:else}
            {#each productHasStockList as data}
                <div class="padding-6 border-b1 border-radius-4 margin-2">
                    <div>
                        <span class="fs-1rem f-nato-bold">{data.branch_office_nm}</span>
                    </div>
                    <div class="mt-10">
                        <span class="fs-1rem pretendard-regular">{data.product_nm}</span>
                    </div>
                    <div class="display-flex align-items justify-content-between">
                        <span class="fs-1rem pretendard-regular">{priceReplace(data.product_price)} 원</span>
                        <div>
                            <span class="fs-14 pretendard-regular">{data.product_weight}{data.product_weight_dt}</span>
                        </div>
                    </div>
                    <div class="mt-10 display-flex align-items justify-content-right">
                        <span class="fs-1rem pretendard-bold color-tomato">재고 : {data.product_stock - data.product_sell - data.product_recall} 개</span>
                    </div>
                </div>
            {/each}
        {/if}
         
    </div>
</div>

<style>
    .list-box {display: grid; grid-template-columns: repeat(auto-fill, minmax(20%, auto)); padding: 2px;}

    @media all and (max-width: 768px){
        .list-box {grid-template-columns: repeat(auto-fill, minmax(40%, auto));}
    }
</style>