<script lang="ts">
    import { createEventDispatcher } from "svelte";
    import { branchOffice } from "../../option/branchOffice";
    import { noti } from "../../option/store";
    import Modal from "../../component/Modal.svelte";


    // 수정
    export let isModal:boolean = false;
    export let branchOfficeSequence:number = 0;
    let branchOfficeName:string = "";
    let barnchOfficeArea:string = "";

    const getBranchOffice = async () => {
        let branchOfficeInfo = await branchOffice.info(branchOfficeSequence);

        branchOfficeSequence = branchOfficeInfo.branch_office_sq;
        branchOfficeName = branchOfficeInfo.branch_office_nm;
        barnchOfficeArea = branchOfficeInfo.branch_office_area;
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

    const dispatch = createEventDispatcher();
    const modifyBranchOffice = async () => {
        let data = {
            branch_office_sq:branchOfficeSequence,
            branch_office_nm:branchOfficeName,
            branch_office_area:barnchOfficeArea, 
        }
        let branchOfficeModify = await branchOffice.modify(branchOfficeSequence, data);
        if(branchOfficeModify === 1){
            isModal = false;
            dispatch("refresh")
            noti.success("지점 수정 완료", 2000)
        }
    }
</script>

<!-- Modify 모달 -->
<Modal bind:isModal={isModal}>
    <span slot="modal-title" class="fs-1rem pretendard-bold color-white">지점 수정</span>

    <div slot="modal-content">
        {#await getBranchOffice() then }
        <!-- 지점명 -->
        <div>
            <span class="fs-16 pretendard-bold">지점명 <span class="color-tomato">*</span></span>
            <div class="mt-10">
                <input type="text" class="fs-16 pretendard-regular border-default border-radius-4 padding-8-12 width-100" bind:value={branchOfficeName} placeholder="지점명 입력"/>
            </div>
        </div>

        <!-- 지점이 위치한 지역이름 -->
        <div class="mt-20">
            <span class="fs-16 pretendard-bold">지점이 위치한 지역이름 <span class="color-tomato">*</span></span>
            <div class="mt-10">
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