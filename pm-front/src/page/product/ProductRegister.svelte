<script lang="ts">
    import SideMenuBar from "../../layout/SideMenuBar.svelte";
    import Header from "../../layout/Header.svelte";
    import axios from "axios";
    import { onMount } from "svelte";
    import { push } from "svelte-spa-router";


    //
    let branchOfficeSq:string = "0";
    let productName:string = "";
    let productSt:string = "1";
    let productPrice:string = "";
    let productCommission:string = "";
    let productWeight:string = "";
    let productWeightDt:string = ""
    let productMeasure:string = "";

    // 지점 선택
    let branchOfficeDatas:any = [];
    const getBranchOfiice = async () => {
        await axios.get("/api/branch-office").then((res)=>{
            branchOfficeDatas = res.data;
        }).catch((err)=>{});
    }

    onMount(()=>{
        getBranchOfiice();
    })
    
    // 냉장 여부
    let isColdDatas = [
        {label:"기본", value:"1", id:"one"},
        {label:"냉장", value:"2", id:"two"},
        {label:"냉동", value:"3", id:"three"},
    ]

    // 무게 표시
    let weightDatas = [
        "mg","g", "kg", "t","ml","l","통","팩","봉지","반통","개"
    ]

    // 숫자만 입력 가능 Method
    const onlyNumberByPrice = () => {
        productPrice = productPrice.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
    }
    const onlyNumberByWeight = () => {
        productWeight = productWeight.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
    }

    // 등록 버튼
    let commissionReg = /^[0-9]{1,3}\.?[0-9]{0,5}$/
    const productRegisterButton = () => {
        if(!branchOfficeSq){
            alert("지점을 선택 해 주세요")
        }else if(!productName){
            alert("상품 명을 입력 해 주세요")
        }else if(!productPrice){
            alert("상품 단가를 입력 해 주세요")
        }else if(!commissionReg.test(productCommission)){
            alert("수수료 소수점은 한개만 표기바람, 숫자만 입력 바람")
        }else if(!productCommission){
            alert("수수료를 입력 해 주세요/n수수료 없을 시 0 표기바람")
        }else if(!productWeight || !productWeightDt){
            alert("단위를 입력, 선택 해 주세요")
        }else{
            productRegister();
            //alert("상품이 등록 되셨습니다");
        }
        
    }

    // 등록 RestApi
    const productRegister = async () => {
        let datas = {
            branch_office_sq : branchOfficeSq,
            product_nm : productName,
            product_st : productSt, 
            product_price : productPrice,
            product_commission : productCommission,
            product_weight : productWeight,
            product_weight_dt : productWeightDt,
            product_measure : productMeasure
        }
        await axios.post("/api/product", datas).then((res)=>{
            push("/product/list")
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
            <p class="fs-20 pretendard-bold">상품 등록</p>
            <div class="mt-10 display-flex align-items gap-5">
                <span class="fs-14 prtendard-regular">상품 관리</span>
                <span class="fs-14 prtendard-regular">/</span>
                <span class="fs-14 prtendard-regular">상품 등록</span>
            </div>
        </div>

        <!--  -->
        <div class="mt-20 padding-12 box-shadow-primary background-color-white">
            <!-- 지점 선택 -->
            <div>
                <span class="fs-1rem pretendard-bold">지점 선택 <span class="color-tomato">*</span></span>
                <div class="mt-10">
                    <div>
                        <select bind:value={branchOfficeSq} class="fs-1rem pretendard-regular mobile-width-100 min-width-250 border-default border-radius-4 padding-6-12">
                            <option class="fs-1rem pretendard-regular" value="0">지점 선택</option>
                            {#each branchOfficeDatas as data}
                                <option class="fs-1rem pretendard-regular" value={data.branch_office_sq}>{data.branch_office_nm}</option>
                            {/each}
                        </select>
                    </div>
                </div>
            </div>

            <!-- 상품 이름 -->
            <div class="mt-20">
                <span class="fs-1rem pretendard-bold">상품명 <span class="color-tomato">*</span></span>
                <div class="mt-10">
                    <input type="text" class="fs-1rem pretendard-regular mobile-width-100 min-width-250 border-default border-radius-4 padding-8-12" bind:value={productName} placeholder="상품 이름 입력"/>
                </div>
            </div>

            <!-- 기본, 냉장, 냉동 여부 -->
            <div class="mt-20">
                <span class="fs-1rem pretendard-bold">냉장, 냉동 여부 <span class="color-tomato">*</span></span>
                <div class="mt-10">
                    {#each isColdDatas as data}
                        <label class="fs-1rem pretendard-regular">
                            <input type="radio" bind:group={productSt} value={data.value} />
                            {data.label}
                        </label>
                    {/each}
                    
                </div>
            </div>

            <!-- 상품 가격 -->
            <div class="mt-20">
                <span class="fs-1rem pretendard-bold">단가 <span class="color-tomato">*</span></span>
                <div class="mt-10">
                    <input type="text" class="fs-1rem pretendard-regular mobile-width-100 min-width-250 border-default border-radius-4 padding-8-12" bind:value={productPrice} on:input={onlyNumberByPrice} maxlength="20" placeholder="상품 단가 입력(숫자만)"/>
                </div>
            </div>

            <!-- 수수료 -->
            <div class="mt-20">
                <span class="fs-1rem pretendard-bold">수수료 <span class="color-tomato">*</span></span>
                <div class="mt-10">
                    <input type="text" class="fs-1rem pretendard-regular mobile-width-100 min-width-250 border-default border-radius-4 padding-8-12" bind:value={productCommission} placeholder="수수료 입력"/>
                    <p class="mt-5 fs-14 pretendard-regular color-b1">Ex) 11% -> 11, 11.5% -> 11.5, 없음은 0으로 표기</p>
                </div>
            </div>

            <!-- 상품 무게 -->
            <div class="mt-20">
                <span class="fs-1rem pretendard-bold">상품 단위 <span class="color-tomato">*</span></span>
                <div class="mt-10 display-flex align-items gap-5">
                    <input type="text" class="mt-5 fs-1rem pretendard-regular mobile-width-100 min-width-250 border-default border-radius-4 padding-8-12" bind:value={productWeight} on:input={onlyNumberByWeight} placeholder="무게 입력"/>
                    <div class="mt-5">
                        <select bind:value={productWeightDt} class="fs-1rem pretendard-regular mobile-width-100 min-width-250 border-default border-radius-4 padding-8-12">
                            <option class="fs-1rem pretendard-regular" value="">단위 선택</option>
                            {#each weightDatas as data}
                                <option class="fs-1rem pretendard-regular" value={data}>{data}</option>
                            {/each}
                        </select>
                    </div>
                </div>
            </div>

            <!-- 상품 단위 -->
            <!-- <div class="mt-20">
                <span class="fs-1rem pretendard-bold">상품 단위</span>
                <div class="mt-10">
                    <input type="text" class="fs-1rem pretendard-regular border-default border-radius-4 padding-8-12" bind:value={productMeasure} placeholder="단위 입력"/>
                    <p class="mt-5 fs-14 pretendard-regular color-b1">Ex) 개, 통, 반통, 봉지, 박스 등</p>
                </div>
            </div> -->

            <!-- 등록 버튼 -->
            <div class="mt-30 ta-c">
                <button type="button" class="fs-1rem pretendard-regular button-primary background-none border-default border-radius-4 padding-8-12" on:click={productRegisterButton}>상품 등록</button>
            </div>

        </div>
    </div>

    <div style="height: 150px;">

    </div>
    
</div>

<style>
    @media all and (max-width: 480px){
        .display-flex {display: block;}
    }
</style>