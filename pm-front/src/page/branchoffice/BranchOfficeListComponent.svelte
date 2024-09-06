<script lang="ts">
    import { link } from "svelte-spa-router";
    import {createEventDispatcher} from 'svelte';
    import Modal from "../../component/Modal.svelte";
    import Loading from "../error/Loading.svelte";
    import { branchOffice } from "../../option/branchOffice";

    export let branchOfficeSq:number = 0;
    export let branchOfficeName:string = "";

    let cp:number = 1; // 첫 페이지 번호
    let ps:number = 10;
    let rowCount:number = 0;
    let keyword:string = "";
    let sp:number; // 시작 페이지
    let ep:number; // 마지막 페이지
    let pageCount:number; // 페이지 개수
    let pageDatas:any = []; 
    let branchOfficeDatas:any = [];
    const getBranchOfficeList = async () => {
        let data = {
            cp: cp, 
            ps: ps,
            keyword:keyword, 
        }
        let branchOfficeList:any = await branchOffice.list(data);

        branchOfficeDatas = branchOfficeList.list;
        rowCount = branchOfficeList.count;
        cp = branchOfficeList.cp;
        ps = branchOfficeList.ps;
        sp = branchOfficeList.sp;
        ep = branchOfficeList.ep;
        pageCount = branchOfficeList.pageCount;

        pageDatas = [];
        for(let i=sp; i<=ep; i++){
            pageDatas.push(i);
            pageDatas = pageDatas;
        }
    }

    // 지점 모달
    export let isBranchOffice:boolean = false;

    // 지점 초이스
    const dispatch = createEventDispatcher();
    const branchOfficeChoice = (sq:number, name:string) => {
        isBranchOffice = false;
        branchOfficeSq = sq;
        branchOfficeName = name;
        dispatch("refresh")
        cp = 1;
    }
</script>

<!-- 지점 리스트 Modal -->
<Modal bind:isModal={isBranchOffice}>
    <span slot="modal-title" class="fs-1rem pretendard-bold color-white">지점 리스트</span>
    <div slot="modal-content">
        {#await getBranchOfficeList()} 
            <Loading/>
        {:then} 
            <div class="display-grid width-100">
                {#if branchOfficeDatas.length === 0}
                    <div class="mt-10 ta-c">
                        <p class="fs-1rem pretendard-regular">등록 된 지점이 없습니다</p>
                        <a href="/branchoffice/register" class="fs-1rem pretendard-regular color-tomato" use:link>지점 등록 하기</a>
                    </div>
                {:else}
                <button type="button" class="fs-16 pretendard-regular button-hover background-none border-none border-bottom-b1 padding-8" on:click={()=>{branchOfficeChoice(0, "")}}>전체 지점</button>
                    {#each branchOfficeDatas as data}
                        <button type="button" class="fs-16 pretendard-regular button-hover background-none border-none border-bottom-b1 padding-8" on:click={()=>{branchOfficeChoice(data.branch_office_sq, data.branch_office_nm)}}>{data.branch_office_nm}</button>
                    {/each}
                    <div class="mt-20 display-flex align-items gap-5">
                        {#if cp !== 1}
                            <div>
                                <button type="button" class="fs-1rem pretendard-regular background-none border-default button-primary padding-4-10" on:click={()=>{cp=cp-1; getBranchOfficeList()}}>이전</button>
                            </div>
                        {/if}
                        
                        <div>
                            {#each pageDatas as data}
                                {#if data === cp}
                                    <button type="button" class="fs-1rem pretendard-regular background-none button-update padding-4-10">{data}</button>    
                                {:else}
                                    <button type="button" class="fs-1rem pretendard-regular background-none border-default button-primary padding-4-10" on:click={()=>{cp=data; getBranchOfficeList()}}>{data}</button>
                                {/if}
                            {/each}
                        </div>
    
                        {#if cp !== pageCount }
                            <div>
                                <button type="button" class="fs-1rem pretendard-regular background-none border-default button-primary padding-4-10" on:click={()=>{cp=cp+1; getBranchOfficeList()}}>다음</button>
                            </div>
                        {/if}
                    </div>
                {/if}
            </div>
        {/await}
    </div>
</Modal>