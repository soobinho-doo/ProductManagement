<script lang="ts">
    import Header from "../../layout/Header.svelte";
    import SideMenuBar from "../../layout/SideMenuBar.svelte";
    import Loading from "../error/Loading.svelte";
    import BranchOfficeModifyComponent from "./BranchOfficeModifyComponent.svelte";

    import { onMount } from "svelte";
    import { link, push } from "svelte-spa-router";
    import {branchOffice} from "../../option/branchOffice";
    import { noti } from "../../option/store";
    import { pageSizeDatas } from "../../option/utill";

    // onMount(() => {
    //     getBranchOfficeList();
    // })

    // 리스트
    let branchOfficeDatas:any = [];
    let cp:number = 1; // 첫 페이지 번호
    let ps:number = 10;
    let rowCount:number = 0;
    let keyword:string = "";
    let sp:number; // 시작 페이지
    let ep:number; // 마지막 페이지
    let pageCount:number; // 페이지 개수
    let pageDatas:any = []; 
    const getBranchOfficeList = async () => {
        let data = {
            cp: cp, 
            ps: ps,
            keyword: keyword, 
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
    let promise = getBranchOfficeList();

    // 수정
    let isModal:boolean = false;
    let branchOfficeSequence:number;

    // Get
    const getBranchOfficeButton = (sq:number) => {
        isModal = !isModal;
        branchOfficeSequence = sq;
    }
    

    // 삭제
    const deleteBranchOfficeButton = (sequence:number) => {
        if(confirm("삭제 하시겠습니까?\n삭제 후 데이터는 복구 되지 않습니다")){
            deleteBranchOffice(sequence);
        }else{
            alert("취소 되었습니다");
        }
    }

    const deleteBranchOffice = async (sequence:number) => {
        let branchOfficeDel = await branchOffice.del(sequence);
        if(branchOfficeDel === 1){
            getBranchOfficeList();
            noti.success("지점 삭제 완료", 2000)
        }
    }

</script>

<SideMenuBar/>

<div class="wrapper">
    <Header/>
    <!-- Content -->
    <div class="padding-12">
        <!-- Title -->
        <div class="mt-10 padding-12 box-shadow-primary background-color-white">
            <p class="fs-20 pretendard-bold">지점 리스트</p>
            <div class="mt-10 display-flex align-items gap-5">
                <span class="fs-14 prtendard-regular">지점관리</span>
                <span class="fs-14 prtendard-regular">/</span>
                <span class="fs-14 prtendard-regular">지점 리스트</span>
            </div>
        </div>

        <!--  -->
        {#await promise}
            <Loading/>
        {:then} 
            <div class="mt-20 padding-12 box-shadow-primary background-color-white">
                <div class="display-flex align-items justify-content-between">
                    <div>
                        <p class="fs-1rem f-nato-bold">등록 지점 {rowCount}</p>
                    </div>
                    <!--  -->
                    <div class="display-flex align-items gap-10">
                        <button type="button" class="fs-1rem f-nato color-custom-blue button-primary background-none border-default border-radius-4 padding-4-12" on:click={()=>{push("/branchoffice/register")}}>지점 등록</button>
                        <select class="fs-1rem f-nato border-b1 border-radius-4 padding-4-12" bind:value={ps} on:change={getBranchOfficeList}>
                            {#each pageSizeDatas as data}
                                <option class="fs-1rem" value={data}>{data}</option>
                            {/each}
                        </select>
                        <button type="button" class="background-none border-b1 border-radius-4 padding-8-10" on:click={()=>{promise = getBranchOfficeList()}}>
                            <svg xmlns="http://www.w3.org/2000/svg" class="svg-fill svg-size" width="18" height="18" viewBox="0 0 24 24">
                                <g fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5">
                                    <path d="M20.5 8c-1.392-3.179-4.823-5-8.522-5C7.299 3 3.453 6.552 3 11.1"/>
                                    <path d="M16.489 8.4h3.97a.54.54 0 0 0 .54-.54V3.9M3.5 16c1.392 3.179 4.823 5 8.522 5c4.679 0 8.525-3.552 8.978-8.1"/>
                                    <path d="M7.511 15.6h-3.97a.54.54 0 0 0-.541.54v3.96"/>
                                </g>
                            </svg>
                        </button>
                    </div>
                </div>

                <!-- 리스트 테이블 -->
                <div class="mt-10 border-right-b1 border-left-b1">
                    <div class="width-100 display-flex align-items bg-light-gray border-top-b1 border-bottom-b1">
                        <span class="fs-125rem f-nato fw-b width-35 padding-8">지점</span>
                        <span class="fs-125rem f-nato fw-b width-35 padding-8 ta-c">지역</span>
                        <span class="fs-125rem f-nato fw-b width-30 padding-8 ta-c">설정</span>
                    </div>
                    {#if branchOfficeDatas.length !== 0}
                        {#each branchOfficeDatas as data}
                            <div class="width-100 display-flex align-items border-bottom-b1">
                                <span class="fs-1rem f-nato width-35 padding-8">{data.branch_office_nm}</span>
                                <span class="fs-1rem f-nato width-35 padding-8 ta-c">{data.branch_office_area}</span>
                                <span class="display-flex align-items justify-content-center gap-10 width-30 padding-8">
                                    <button type="button" class="fs-1rem f-nato button-update border-radius-4 padding-4-8" on:click={()=>{getBranchOfficeButton(data.branch_office_sq)}}>수정</button>
                                    <button type="button" class="fs-1rem f-nato button-delete border-radius-4 padding-4-8" on:click={()=>{deleteBranchOfficeButton(data.branch_office_sq)}}>삭제</button>
                                </span>
                            </div>
                        {/each}
                    {:else}
                        <div class="mt-30 ta-c">
                            <p class="fs-1rem pretendard-regular">등록 된 지점이 없습니다</p>
                            <a href="/branchoffice/register" class="fs-1rem pretendard-regular color-tomato" use:link>지점 등록 하기</a>
                        </div>
                    {/if}
                </div>

                <!-- 페이징 -->
                {#if rowCount >= 10}
                    <div class="mt-30 display-flex align-items gap-5">
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
    
        <!-- div height -->
        <div style="height: 100;"></div>

    </div>

</div>

<BranchOfficeModifyComponent bind:isModal={isModal} bind:branchOfficeSequence={branchOfficeSequence} on:refresh={getBranchOfficeList}/>