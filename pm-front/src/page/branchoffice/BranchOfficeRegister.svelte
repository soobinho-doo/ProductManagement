<script lang="ts">
    import Header from "../../layout/Header.svelte";
    import SideMenuBar from "../../layout/SideMenuBar.svelte";
    import { branchOffice } from "../../option/branchOffice";

    let branchOfficeName:string = "";
    let barnchOfficeArea:string = "";

    const branchOfficeRegisterButton = () =>{
        if(!branchOfficeName){
            alert("지점명을 입력 해 주세요")
        }else if(!barnchOfficeArea){
            alert("지점명이 위치한 지역을 입력 해 주세요")
        }else{
            branchOfficeRegister();
        }
    }

    const branchOfficeRegister = async () => {
        let data = {
            branch_office_nm:branchOfficeName,
            branch_office_area:barnchOfficeArea, 
        }
        await branchOffice.input(data);
    }

</script>

<SideMenuBar/>

<div class="wrapper">
    <Header/>
    <!-- Content -->
    <div class="padding-12">
        <!-- Title -->
        <div class="mt-10 padding-12 box-shadow-primary background-color-white">
            <p class="fs-20 pretendard-bold">지점 등록</p>
            <div class="mt-10 display-flex align-items gap-5">
                <span class="fs-14 prtendard-regular">지점관리</span>
                <span class="fs-14 prtendard-regular">/</span>
                <span class="fs-14 prtendard-regular">지점 등록</span>
            </div>
        </div>

        <!--  -->
        <div class="mt-20 padding-12 box-shadow-primary background-color-white">
            <!-- 지점명 -->
            <div>
                <span class="fs-16 pretendard-bold">지점명 <span class="color-tomato">*</span></span>
                <div class="mt-10">
                    <input type="text" class="fs-16 pretendard-regular mobile-width-100 min-width-250 border-default border-radius-4 padding-8-12" bind:value={branchOfficeName} placeholder="지점명 입력" maxlength="100"/>
                </div>
            </div>

            <!-- 지점이 위치한 지역이름 -->
            <div class="mt-20">
                <span class="fs-16 pretendard-bold">지점이 위치한 지역이름 <span class="color-tomato">*</span></span>
                <div class="mt-10">
                    <input type="text" class="fs-16 pretendard-regular mobile-width-100 min-width-250 border-default border-radius-4 padding-8-12" bind:value={barnchOfficeArea} placeholder="지점이 위치한 지역이름 입력" maxlength="50"/>
                    <p class="mt-5 fs-14 pretendard-regular color-b1">Ex) 서울, 전주 등</p>
                </div>
            </div>

            <!-- 등록 버튼 -->
            <div class="mt-30 ta-c">
                <button type="button" class="fs-16 pretendard-regular button-primary background-none border-default border-radius-4 padding-8-12" on:click={branchOfficeRegisterButton}>지점 등록</button>
            </div>
        </div>

    </div>

</div>