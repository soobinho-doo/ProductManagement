<script lang="ts">
    import axios from "axios";
    import { priceReplace } from "../../option/utill";

    //
    let today = new Date();
    let year = today.getFullYear();
    let monthList:any = [];
    const getMonthList = async () => {
        await axios.get("/api/status/month/"+year).then((res)=>{
            monthList = res.data;
        }).catch((err)=>{});
    }

    let promise = getMonthList();

    const minusYear = () => {
        year = year - 1
        getMonthList();
    }

    const plusYear = () => {
        year = year + 1
        getMonthList();
    }

</script>

{#await promise}
    <div class="mt-20 ta-c">
        <p class="pretendard-regular">데이터를 가져오는 중 입니다</p>
    </div>
{:then} 
    <div class="mt-20">
        <div class="mt-10 display-flex align-items">
            <button type="button" class="background-none border-none width-100" on:click={minusYear}>
                <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 48 48">
                    <path fill="none" stroke="#000" stroke-linecap="round" stroke-linejoin="round" stroke-width="4" d="M31 36L19 24L31 12"/>
                </svg>
            </button>
            <span class="fs-20 pretendard-bold ta-c width-100">{year} 년</span>
            <button type="button" class="background-none border-none width-100" on:click={plusYear}>
                <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 48 48">
                    <path fill="none" stroke="#000" stroke-linecap="round" stroke-linejoin="round" stroke-width="4" d="M19 12L31 24L19 36"/>
                </svg>
            </button>
        </div>
        {#if monthList.length === 0}
            <div class="mt-10 ta-c">
                <p class="pretendard-regular">데이터가 없습니다</p>
            </div>
        {:else}
            <div class="mt-10">
                {#each monthList as data}
                    <div class="mt-10 padding-6 border-b1 border-radius-4">
                        <p class="fs-1rem pretendard-bold">{data.months} 월</p>
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
            </div>
        {/if}  
    </div>
{/await}