<script lang="ts">
    import Header from "../../layout/Header.svelte";
    import SideMenuBar from "../../layout/SideMenuBar.svelte";
    import BranchOfficeModifyComponent from "./BranchOfficeModifyComponent.svelte";
    import { onMount } from "svelte";
    import { link, push } from "svelte-spa-router";
    import {branchOffice} from "../../option/branchOffice";
    import { noti } from "../../option/store";

    onMount(() => {
        getBranchOfficeList();
    })

    // 리스트
    let branchOfficeDatas:any = [];
    let cp:number = 1; // 첫 페이지 번호
    let rowCount:number = 0;
    let keyword:string = "";
    let sp:number; // 시작 페이지
    let ep:number; // 마지막 페이지
    let pageCount:number; // 페이지 개수
    let pageDatas:any = []; 
    const getBranchOfficeList = async () => {
        let data = {
            cp: cp, 
            keyword: keyword, 
        }
        
        let branchOfficeList:any = await branchOffice.list(data);
            
        branchOfficeDatas = branchOfficeList.list;
        rowCount = branchOfficeList.count;
        cp = branchOfficeList.cp;
        sp = branchOfficeList.sp;
        ep = branchOfficeList.ep;
        pageCount = branchOfficeList.pageCount;

        pageDatas = [];
        for(let i=sp; i<=ep; i++){
            pageDatas.push(i);
            pageDatas = pageDatas;
        }

    }

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
        <div class="mt-20 padding-12 box-shadow-primary background-color-white">
            <div class="display-flex align-items justify-content-between">
                <div>
                    <p class="fs-1rem pretendard-bold">등록 지점 {rowCount}</p>
                </div>
                <!--  -->
                <div class="display-flex align-items">
                    <button type="button" class="fs-1rem pretendard-regular button-primary background-none border-default border-radius-4 padding-6-12" on:click={()=>{push("/branchoffice/register")}}>지점 등록</button>
                </div>
            </div>
            

            <!-- 컬럼 -->
            <div class="mt-10 display-table width-100">
                <div class="display-table-row">
                    <div class="display-table-cell border-b1 padding-6">
                        <span class="fs-125rem pretendard-bold">지점</span>
                    </div>
                    <div class="display-table-cell ta-c border-b1 padding-6">
                        <span class="fs-125rem pretendard-bold">지역</span>
                    </div>
                    <div class="display-table-cell ta-c border-b1 padding-6">
                        <span class="fs-125rem pretendard-bold">변경 사항</span>
                    </div>
                </div>
            

                <!-- Body -->
                <!-- <div class="mt-10 display-table width-100"> -->
                {#if branchOfficeDatas.length !== 0}
                    {#each branchOfficeDatas as data}
                        <div class="mt-10 display-table-row">
                            <div class="display-table-cell border-b1 padding-6">
                                <span class="fs-1rem prtendard-regular">{data.branch_office_nm}</span>
                            </div>
                            <div class="display-table-cell ta-c border-b1 padding-6">
                                <span class="fs-1rem prtendard-regular">{data.branch_office_area}</span>
                            </div>
                            <div class="display-table-cell ta-c border-b1 padding-6">
                                <button type="button" class="fs-1rem pretendard-regular button-update border-radius-4 padding-4-8" on:click={()=>{getBranchOfficeButton(data.branch_office_sq)}}>수정</button>
                                <button type="button" class="fs-1rem pretendard-regular button-delete border-radius-4 padding-4-8" on:click={()=>{deleteBranchOfficeButton(data.branch_office_sq)}}>삭제</button>
                            </div>
                        </div>
                    {/each}
                {/if}
                
            </div>
            {#if branchOfficeDatas.length === 0}
                <div class="mt-30 ta-c">
                    <p class="fs-1rem pretendard-regular">등록 된 지점이 없습니다</p>
                    <a href="/branchoffice/register" class="fs-1rem pretendard-regular color-tomato" use:link>지점 등록 하기</a>
                </div>
            {/if}

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

        <!-- div height -->
        <div style="height: 100;"></div>

    </div>

</div>

<BranchOfficeModifyComponent bind:isModal={isModal} bind:branchOfficeSequence={branchOfficeSequence} on:refresh={getBranchOfficeList}/>