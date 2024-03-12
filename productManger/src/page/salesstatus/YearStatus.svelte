<script lang="ts">
    import axios from "axios";
    import { priceReplace } from "../../option/utill";

    //
    let yearList:any = [];
    const getYearList = async () => {
        await axios.get("/api/status/year").then((res)=>{
            yearList = res.data;
        }).catch((err)=>{});
    }

    let promise = getYearList();
</script>

{#await promise}
    <div class="mt-20 ta-c">
        <p class="pretendard-regular">데이터를 가져오는 중 입니다</p>
    </div>
{:then} 
    <div class="mt-20">
        {#if yearList.length === 0}
            <div class="ta-c">
                <p class="pretendard-regular">데이터가 없습니다</p>
            </div>
        {:else}
            {#each yearList as data}
                <div class="mt-10 padding-6 border-b1 border-radius-4">
                    <p class="fs-1rem pretendard-bold">{data.years} 년</p>
                    <div class="display-grid align-items justify-content-right">
                        <div class="display-flex align-items gap-10">
                            <sapn class="fs-1rem pretendard-regular">판매 총액</sapn>
                            <sapn class="fs-1rem pretendard-regular">{priceReplace(data.sum_price)}</sapn>
                        </div>
                        <div class="mt-5 display-flex align-items gap-10">
                            <sapn class="fs-1rem pretendard-regular">수익 총액</sapn>
                            <sapn class="fs-1rem pretendard-regular">{priceReplace(data.sum_commission_price)}</sapn>
                        </div>
                    </div>
                </div>
            {/each}
        {/if}  
    </div>
{/await}