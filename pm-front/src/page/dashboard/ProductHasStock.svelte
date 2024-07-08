<script lang="ts">
    import { onMount } from "svelte";
    import { priceReplace } from "../../option/utill";
    import { push } from "svelte-spa-router";
    import { dashBaord } from "../../option/dashboard";

    onMount( async ()=>{
        stockList();
    })

    let prodcutHasStockList:any = [];
    const stockList = async () => {
        prodcutHasStockList = await dashBaord.stockList();
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
    
    <div class="mt-10 list-box">
        {#if prodcutHasStockList.length === 0}
            <div>
                <p class="fs-18 pretendard-regular display-flex align-items gap-5">재고가 없습니다</p>
            </div>
        {:else}
            {#each prodcutHasStockList as data}
                <div class="padding-6 border-b1 border-radius-4 margin-2">
                    <div>
                        <span class="fs-1rem pretendard-regular">{data.branch_office_nm}</span>
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
    .margin-2 {margin: 2px;}

    @media all and (max-width: 768px){
        .list-box {grid-template-columns: repeat(auto-fill, minmax(40%, auto));}
    }
</style>