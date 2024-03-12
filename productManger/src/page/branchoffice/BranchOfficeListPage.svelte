<script lang="ts">
    import Header from "../../layout/Header.svelte";
    import SideMenuBar from "../../layout/SideMenuBar.svelte";
    import Modal from "../../component/Modal.svelte";
    import axios from "axios";
    import { onMount } from "svelte";
    import { link, push } from "svelte-spa-router";

    onMount(() => {
        getBranchOfficeList(cp);
    })

    // 등록
    let branchOfficeDatas:any = [];
    let cp:number; // 첫 페이지 번호
    let rowCount:number = 0;
    let keyword:string = "";
    let sp:number; // 시작 페이지
    let ep:number; // 마지막 페이지
    let pageCount:number; // 페이지 개수
    let pageDatas:any = []; 
    const getBranchOfficeList = async (cpNum:any) => {
        if(!cpNum){
            cpNum = 1;
        }
        let data = {
            cp: cpNum, 
            keyword:keyword, 
        }
        await axios.post("/api/branch-office/paging", data).then((res) => {
            //console.log(res.data);
            branchOfficeDatas = res.data.list;
            rowCount = res.data.count;
            cp = res.data.cp;
            sp = res.data.sp;
            ep = res.data.ep;
            pageCount = res.data.pageCount;

            pageDatas = [];
            for(let i=sp; i<=ep; i++){
                pageDatas.push(i);
                pageDatas = pageDatas;
            }
        }).catch((err) => {});
    }

    // 수정
    let isModal:boolean = false;
    let sequence:number;
    let branchOfficeName:string = "";
    let barnchOfficeArea:string = "";

    // Get
    const getBranchOfficeButton = (sq:number) => {
        isModal = !isModal;
        sequence = sq;
    }
    const getBranchOffice = async () => {
        await axios.get("/api/branch-office/"+sequence).then((res) =>{
            sequence = res.data.branch_office_sq;
            branchOfficeName = res.data.branch_office_nm;
            barnchOfficeArea = res.data.branch_office_area;
        }).catch((err)=>{})
    }

    //
    const modifyBranchOfficeButton = () =>{
        if(!branchOfficeName){
            alert("지점명을 입력 해 주세요")
        }else if(!barnchOfficeArea){
            alert("지점명이 위치한 지역을 입력 해 주세요")
        }else{
            modifyBranchOffice();
        }
    }

    const modifyBranchOffice = async () => {
        let datas = {
            branch_office_sq:sequence,
            branch_office_nm:branchOfficeName,
            branch_office_area:barnchOfficeArea, 
        }
        await axios.put("/api/branch-office/"+sequence, datas).then((res)=>{
            isModal = false;
            getBranchOfficeList(cp);
        }).catch((err)=>{});
    }

    // 삭제
    const deleteBranchOfficeButton = (sq:number) => {
        if(confirm("삭제 하시겠습니까?\n삭제 후 데이터는 복구 되지 않습니다")){
            alert("정상적으로 삭제되었습니다.");
            deleteBranchOffice(sq);
        }else{
            alert("취소 되었습니다");
        }
    }

    const deleteBranchOffice = async (sq:number) => {
        await axios.delete("/api/branch-office/"+sq).then((res)=>{
            getBranchOfficeList(cp);
        }).catch((err)=>{});
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
                    <p class="fs-1rem pretendard-regular">등록 지점 {rowCount}</p>
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
                                <button type="button" class="fs-1rem pretendard-regular button-update padding-4-8" on:click={()=>{getBranchOfficeButton(data.branch_office_sq)}}>수정</button>
                                <button type="button" class="fs-1rem pretendard-regular button-delete padding-4-8" on:click={()=>{deleteBranchOfficeButton(data.branch_office_sq)}}>삭제</button>
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
                            <button type="button" class="fs-1rem pretendard-regular background-none border-default button-primary padding-4-10" on:click={()=>{getBranchOfficeList(cp-1)}}>이전</button>
                        </div>
                    {/if}
                    
                    <div>
                        {#each pageDatas as data}
                            {#if data === cp}
                                <button type="button" class="fs-1rem pretendard-regular background-none button-update padding-4-10">{data}</button>    
                            {:else}
                                <button type="button" class="fs-1rem pretendard-regular background-none border-default button-primary padding-4-10" on:click={()=>{getBranchOfficeList(data)}}>{data}</button>
                            {/if}
                        {/each}
                    </div>

                    {#if cp !== pageCount }
                        <div>
                            <button type="button" class="fs-1rem pretendard-regular background-none border-default button-primary padding-4-10" on:click={()=>{getBranchOfficeList(cp+1)}}>다음</button>
                        </div>
                    {/if}
                </div>   
            {/if}
            
        </div>

        <!-- div height -->
        <div style="height: 100;"></div>

    </div>

</div>

<!-- 업데이트 모달 -->
<Modal bind:isModal={isModal}>
    <span slot="modal-title" class="fs-1rem pretendard-bold color-white">지점 수정</span>

    <div slot="modal-content">
        {#await getBranchOffice() then }
        <!-- 지점명 -->
        <div>
            <span class="fs-16 pretendard-bold">지점명 <span class="color-tomato">*</span></span>
            <div class="mt-5">
                <input type="text" class="fs-16 pretendard-regular border-default border-radius-4 padding-8-12 width-100" bind:value={branchOfficeName} placeholder="지점명 입력"/>
            </div>
        </div>

        <!-- 지점이 위치한 지역이름 -->
        <div class="mt-20">
            <span class="fs-16 pretendard-bold">지점이 위치한 지역이름 <span class="color-tomato">*</span></span>
            <div class="mt-5">
                <input type="text" class="fs-16 pretendard-regular border-default border-radius-4 padding-8-12 width-100" bind:value={barnchOfficeArea} placeholder="지점이 위치한 지역이름 입력"/>
                <p class="mt-5 fs-14 pretendard-regular color-b1">Ex) 서울, 전주 등</p>
            </div>
        </div>

        <!-- 수정 버튼 -->
        <div class="mt-30 ta-c">
            <button type="button" class="fs-16 pretendard-regular button-primary background-none border-default border-radius-4 padding-8-12" on:click={modifyBranchOfficeButton}>지점 수정</button>
        </div>
        {/await}
    </div>
</Modal>

<style>

</style>