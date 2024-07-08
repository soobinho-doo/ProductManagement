<script lang="ts">
    import { priceReplace } from "../../option/utill";
    import { sales } from "../../option/sales"

    let sumPrice:number = 0; 
    let sumCommissionPrice:number = 0;
    let datas:any = []
    const getTodaySalesStatus = async () => {
        datas = await sales.todayStatus();
        sumPrice = datas.sum_price;
        sumCommissionPrice = datas.sum_commission_price
    }
    let promise = getTodaySalesStatus();

</script>

{#await promise}
    <div class="padding-12 box-shadow-primary background-color-white">
        <div class="display-flex align-items justify-content-center">
            <p class="fs-20 pretendard-bold">새로고침 해주세요</p>
        </div>
    </div>
{:then} 
    {#if !datas}
        <div class="padding-12 box-shadow-primary background-color-white">
            <div class="display-flex align-items justify-content-between">
                <sapn class="fs-20 pretendard-regular">오늘 판매 총액</sapn>
                <sapn class="fs-20 pretendard-regular">0 원</sapn>
            </div>
            <div class="mt-10 display-flex align-items justify-content-between">
                <sapn class="fs-20 pretendard-regular">오늘 수익 총액</sapn>
                <sapn class="fs-20 pretendard-regular">0 원</sapn>
            </div>
        </div>
    {:else}
        <div class="padding-12 box-shadow-primary background-color-white">
            <div class="display-flex align-items justify-content-between">
                <sapn class="fs-20 pretendard-regular">오늘 판매 총액</sapn>
                <sapn class="fs-20 pretendard-regular">{priceReplace(sumPrice)} 원</sapn>
            </div>
            <div class="mt-10 display-flex align-items justify-content-between">
                <sapn class="fs-20 pretendard-regular">오늘 수익 총액</sapn>
                <sapn class="fs-20 pretendard-regular">{priceReplace(sumCommissionPrice)} 원</sapn>
            </div>
        </div>
    {/if}
{/await}