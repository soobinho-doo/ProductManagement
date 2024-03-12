<script lang="ts">
    import SideMenuBar from "../../layout/SideMenuBar.svelte";
    import Header from "../../layout/Header.svelte";
    import axios from "axios";
    import { onMount } from "svelte";
    import { push } from "svelte-spa-router";

    let selectProduct:any = [];
    // 
    let stockSt:string = "1";
    let productSq:string = selectProduct.product_sq;
    let stockNo:number;
    let today = new Date();
    let year = today.getFullYear();
    let month = String(today.getMonth() + 1).padStart(2, "0");
    let date = String(today.getDate()).padStart(2, "0");
    let stockDate:string = year + '-' + month + '-' + date;

    // 납품, 판매, 회수 여부
    let stockDatas = [
        {label:"납품", value:"1"},
        {label:"판매", value:"2"},
        {label:"회수", value:"3"},
    ]

    // Get BranchOfficeList
    let branchOfficeDatas:any = [];
    let bracnhOfficeName:string = "";
    const getBranchOfiice = async () => {
        await axios.get("/api/branch-office").then((res)=>{
            branchOfficeDatas = res.data;
        }).catch((err)=>{});
    }

    // Get ProductList
    let productDatas:any = [];
    const getProductList = async () => {
        await axios.get("/api/product/list/"+bracnhOfficeName).then((res)=>{
            productDatas = res.data;
        }).catch((err)=>{});
    }

    onMount(()=>{
        getBranchOfiice();
    })

    // 등록 버튼
    const stockRegisterButton = () => {
        //console.log(selectProduct.product_sq)
        if(!selectProduct.product_sq){
            alert("상품을 선택 해 주세요")
        }else if(stockNo === 0){
            alert("개수를 입력 해 주세요")
        }else{
            stockRegister();
            //alert("상품이 등록 되셨습니다");
        }
        
    }

    // 등록 RestApi
    const stockRegister = async () => {
        let datas = {
            stock_st : stockSt,
            product_sq : selectProduct.product_sq,
            stock_no : stockNo, 
            stock_dt : stockDate,
        }
        await axios.post("/api/stock", datas).then((res)=>{
            push("/stock/list")
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
            <p class="fs-20 pretendard-bold">재고 등록</p>
            <div class="mt-10 display-flex align-items gap-5">
                <span class="fs-14 prtendard-regular">재고 관리</span>
                <span class="fs-14 prtendard-regular">/</span>
                <span class="fs-14 prtendard-regular">재고 등록</span>
            </div>
        </div>

        <!--  -->
        <div class="mt-20 padding-12 box-shadow-primary background-color-white">
            <!-- 납품, 판매, 회수 여부 -->
            <div>
                <span class="fs-1rem pretendard-bold">구분 여부 <span class="color-tomato">*</span></span>
                <div class="mt-10 display-table border-radius-4">
                    {#each stockDatas as data}
                        <div class="display-table-cell border-default">
                            <button type="button" class="{stockSt === data.value ? "button-update":""} fs-1rem pretendard-regular border-none background-none padding-6-12" on:click={()=>{stockSt = data.value}}>{data.label}</button>
                        </div>
                    {/each}
                </div>
            </div>

            <!-- 지점 선택 -->
            <div class="mt-20">
                <span class="fs-1rem pretendard-bold">지점 선택 <span class="color-tomato">*</span></span>
                <div class="mt-10">
                    <select bind:value={bracnhOfficeName} class="fs-1rem pretendard-regular mobile-width-100 min-width-250 border-default border-radius-4 padding-6-12" on:change={getProductList}>
                        {#each branchOfficeDatas as data}
                            <option class="fs-1rem pretendard-regular" value={data.branch_office_nm}>{data.branch_office_nm}</option>
                        {/each}
                    </select>
                </div>
            </div>

            <!-- 상품 선택 -->
            <div class="mt-20">
                <span class="fs-1rem pretendard-bold">상품 선택 <span class="color-tomato">*</span></span>
                <div class="mt-10">
                    <div>
                        <select bind:value={selectProduct} class="fs-1rem pretendard-regular mobile-width-100 min-width-250 border-default border-radius-4 padding-6-12">
                            {#if bracnhOfficeName}
                                {#each productDatas as data}
                                    <option class="fs-1rem pretendard-regular" value={data}>{data.product_nm}  &nbsp;{data.product_price} &nbsp;{data.branch_office_nm}</option>
                                {/each}
                            {:else}
                                <option class="fs-1rem pretendard-regular">지점을 선택 해 주세요</option>
                            {/if}
                        </select>
                    </div>
                    <div>
                        {#if selectProduct.length !== 0}
                            <div class="mt-10 display-table">
                                <div class="display-table-row border-b1">
                                    <div class="display-table-cell border-right-b1 padding-6">
                                        <span class="fs-1rem pretendard-regular">지점</span>
                                    </div>
                                    <div class="display-table-cell padding-6">
                                        <span class="fs-1rem pretendard-regular">{selectProduct.branch_office_nm}</span>
                                    </div>
                                </div>
                                <div class="display-table-row border-b1">
                                    <div class="display-table-cell border-right-b1 padding-6">
                                        <span class="fs-1rem pretendard-regular">상품</span>
                                    </div>
                                    <div class="display-table-cell padding-6">
                                        <span class="fs-1rem pretendard-regular">{selectProduct.product_nm}</span>
                                    </div>
                                </div>
                                <div class="display-table-row border-b1">
                                    <div class="display-table-cell border-right-b1 padding-6">
                                        <span class="fs-1rem pretendard-regular">냉장 • 냉동 여부</span>
                                    </div>
                                    <div class="display-table-cell padding-6">
                                        {#if selectProduct.product_st === "1"}
                                            <span class="fs-1rem pretendard-regular">기본</span>
                                        {:else if selectProduct.product_st === "2"}
                                            <span class="fs-1rem pretendard-regular">냉장</span>
                                        {:else if selectProduct.product_st === "3"}
                                            <span class="fs-1rem pretendard-regular">냉동</span>
                                        {/if}
                                    </div>
                                </div>
                                <div class="display-table-row border-b1">
                                    <div class="display-table-cell border-right-b1 padding-6">
                                        <span class="fs-1rem pretendard-regular">단가</span>
                                    </div>
                                    <div class="display-table-cell padding-6">
                                        <span class="fs-1rem pretendard-regular">{selectProduct.product_price}</span>
                                    </div>
                                </div>
                                <div class="display-table-row border-b1">
                                    <div class="display-table-cell border-right-b1 padding-6">
                                        <span class="fs-1rem pretendard-regular">단위</span>
                                    </div>
                                    <div class="display-table-cell padding-6">
                                        <span class="fs-1rem pretendard-regular">{selectProduct.product_weight} {selectProduct.product_weight_dt}</span>
                                    </div>
                                </div>
                                <div class="display-table-row border-b1">
                                    <div class="display-table-cell border-right-b1 padding-6">
                                        <span class="fs-1rem pretendard-regular">수수료</span>
                                    </div>
                                    <div class="display-table-cell padding-6">
                                        <span class="fs-1rem pretendard-regular">{selectProduct.product_commission} %</span>
                                    </div>
                                </div>
                                
                            </div>
                            
                        {/if}
                    </div>
                </div>
            </div>

            <!-- 개수 -->
            <div class="mt-20">
                <span class="fs-1rem pretendard-bold">개수 <span class="color-tomato">*</span></span>
                <div class="mt-10">
                    <input type="number" class="fs-1rem pretendard-regular mobile-width-100 min-width-250 border-default border-radius-4 padding-8-12" bind:value={stockNo} placeholder="개수 입력"/>
                </div>
            </div>

            <!-- 날짜 -->
            <div class="mt-20">
                <span class="fs-1rem pretendard-bold">납품 ⦁ 판매 ⦁ 회수 당일 날짜 <span class="color-tomato">*</span></span>
                <div class="mt-10">
                    <input type="date" class="fs-1rem pretendard-regular mobile-width-100 min-width-250 border-default border-radius-4 padding-8-12" bind:value={stockDate}/>
                </div>
            </div>

            <!-- 등록 버튼 -->
            <div class="mt-30 ta-c">
                <button type="button" class="fs-1rem pretendard-regular button-primary background-none border-default border-radius-4 padding-12-16" on:click={stockRegisterButton}>재고 등록</button>
            </div>

        </div>

    </div>

    <div style="height: 100px;">

    </div>

</div>