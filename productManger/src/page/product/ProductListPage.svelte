<script lang="ts">
    import Header from "../../layout/Header.svelte";
    import SideMenuBar from "../../layout/SideMenuBar.svelte";
    import Modal from "../../component/Modal.svelte";
    import axios from "axios";
    import { onMount } from "svelte";
    import { link, push } from "svelte-spa-router";
    import { priceReplace, mathRound } from "../../option/utill";

    onMount(() => {
        getProductList(cp);
        getBranchOffice();
    })

    // 리스트 Get
    let productDatas:any = [];
    let cp:number; // 첫 페이지 번호
    let rowCount:number = 0;
    let keyword:string = "";
    let branchOfficeNm:string = "";
    let sp:number; // 시작 페이지
    let ep:number; // 마지막 페이지
    let pageCount:number; // 페이지 개수
    let inCommission:string = "";
    let exCommission:string = "";
    let pageDatas:any = []; 
    const getProductList = async (cpNum:any) => {
        if(!cpNum){
            cpNum = 1;
        }
        let data = {
            cp: cpNum, 
            keyword:keyword, 
            branch_office_nm:branchOfficeNm,
        }
        await axios.post("/api/product/paging", data).then((res) => {
            //console.log(res.data);
            productDatas = res.data.list;
            rowCount = res.data.count;
            cp = res.data.cp;
            sp = res.data.sp;
            ep = res.data.ep;
            pageCount = res.data.pageCount;
            inCommission = res.data.inCommission;
            exCommission = res.data.exCommission;

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

    let branchOfficeSq:string = "0";
    let productName:string = "";
    let productSt:string = "";
    let productPrice:string = "";
    let productCommission:string = "0";
    let productWeight:string = "0";
    let productWeightDt:string = ""
    let productMeasure:string = "";

    // 지점 선택
    let branchOfficeDatas:any = [];
    const getBranchOffice = async () => {
        await axios.get("/api/branch-office").then((res)=>{
            branchOfficeDatas = res.data;
        }).catch((err)=>{});
    }

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

    // Get
    const getProductButton = (sq:number) => {
        isModal = !isModal;
        sequence = sq;
    }
    const getProduct = async () => {
        await axios.get("/api/product/"+sequence).then((res) =>{
            branchOfficeSq = res.data.branch_office_sq;
            productName = res.data.product_nm;
            productSt = res.data.product_st;
            productPrice = res.data.product_price;
            productCommission = res.data.product_commission;
            productWeight = res.data.product_weight;
            productWeightDt = res.data.product_weight_dt;
            productMeasure = res.data.product_measure;
        }).catch((err)=>{})
    }

    //
    const modifyProductButton = () =>{
        let commissionReg = /^[0-9]{1,3}\.?[0-9]{0,5}$/
        if(!branchOfficeSq){
            alert("지점을 선택 해 주세요")
        }else if(!productName){
            alert("상품 명을 입력 해 주세요")
        }else if(!productPrice){
            alert("상품 단가을 입력 해 주세요")
        }else if(!commissionReg.test(productCommission)){
            alert("수수료 소수점은 한개만 표기바람, 숫자만 입력 바람")
        }else if(!productCommission){
            alert("수수료를 입력 해 주세요/n수수료 없을 시 0 표기바람")
        }else if(!productWeight || !productWeightDt){
            alert("단위를 입력, 선택 해 주세요")
        }else{
            modifyProduct();
        }
    }

    const modifyProduct = async () => {
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
        await axios.put("/api/product/"+sequence, datas).then((res)=>{
            isModal = false;
            getProductList(cp);
        }).catch((err)=>{});
    }


    // 삭제
    const deleteProductButton = (sq:number) => {
        if(confirm("삭제 하시겠습니까?\n삭제 후 데이터는 복구 되지 않습니다")){
            alert("정상적으로 삭제되었습니다.");
            deleteProduct(sq);
        }else{
            alert("취소 되었습니다");
        }
    }

    const deleteProduct = async (sq:number) => {
        await axios.delete("/api/product/"+sq).then((res)=>{
            getProductList(cp);
        }).catch((err)=>{});
    }

    // Excel Download
    const productListByExcel = async () => {
        await axios.get("/api/product/authentication").then((res)=>{
            location.href="/api/product/excel/download?user_id="+res.data+"&keyword="+keyword+"&branch_office_nm="+branchOfficeNm
        }).catch((err)=>{})
    }
</script>

<SideMenuBar/>

<div class="wrapper">
    <Header/>
    <!-- Content -->
    <div class="padding-12">
        <!-- Title -->
        <div class="mt-10 padding-12 box-shadow-primary background-color-white">
            <p class="fs-20 pretendard-bold">상품 리스트</p>
            <div class="mt-10 display-flex align-items gap-5">
                <span class="fs-14 prtendard-regular">상품 관리</span>
                <span class="fs-14 prtendard-regular">/</span>
                <span class="fs-14 prtendard-regular">상품 리스트</span>
            </div>
        </div>

        <!--  -->
        <div class="mt-20 padding-12 box-shadow-primary background-color-white">
            <!-- Search -->
            
            <div class="search-box gap-10">
                <div class="mt-10">
                    <select bind:value={branchOfficeNm} class="fs-1rem pretendard-regular border-default border-radius-4 padding-6-12 when-480" on:change={()=>{cp = 1; getProductList(cp);}}>
                        <option class="fs-1rem pretendard-regular" value="">전체 지점</option>
                        {#each branchOfficeDatas as data}
                            <option class="fs-1rem pretendard-regular" value={data.branch_office_nm}>{data.branch_office_nm}</option>
                        {/each}
                    </select>
                </div>
                <div class="mt-10 display-flex align-items">
                    <input type="search" class="fs-16 pretendard-regular border-default inp-default padding-8-12 when-480" bind:value={keyword} on:input={()=>{cp = 1; getProductList(cp)}} placeholder="상품 명 입력"/>
                    <button type="button" class="border-none background-none padding-4" on:click={()=>{getProductList(cp)}}>
                        <svg xmlns="http://www.w3.org/2000/svg" class="svg-color-change" width="18" height="18" viewBox="0 0 512 512">
                            <path d="M416 208c0 45.9-14.9 88.3-40 122.7L502.6 457.4c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L330.7 376c-34.4 25.2-76.8 40-122.7 40C93.1 416 0 322.9 0 208S93.1 0 208 0S416 93.1 416 208zM208 352a144 144 0 1 0 0-288 144 144 0 1 0 0 288z"/>
                        </svg>
                    </button>
                </div>
            </div>
            

            <div class="mt-20 display-flex align-items justify-content-between">
                <span class="fs-1rem pretendard-regular">등록 상품 {rowCount}</span>
                <div class="display-flex align-items gap-5">
                    <button type="button" class="fs-1rem pretendard-regular button-primary background-none border-default border-radius-4 padding-6-12" on:click={()=>{push("/product/register")}}>상품 등록</button>
                    <button type="button" class="display-flex align-items gap-5 excel-btn background-color-white border-radius-4 padding-4-8" on:click={productListByExcel}>
                        <svg xmlns="http://www.w3.org/2000/svg" class="excel-svg" width="24" height="22" viewBox="0 0 48 48">
                            <g fill="none" stroke="green" stroke-linecap="round" stroke-width="4">
                                <path stroke-linejoin="round" d="M8 15V6a2 2 0 0 1 2-2h28a2 2 0 0 1 2 2v36a2 2 0 0 1-2 2H10a2 2 0 0 1-2-2v-9"/>
                                <path d="M31 15h3m-6 8h6m-6 8h6"/>
                                <path stroke-linejoin="round" d="M4 15h18v18H4zm6 6l6 6m0-6l-6 6"/>
                            </g>
                        </svg>
                        <span class="fs-1rem pretendard-regular">엑셀</span>
                    </button>
                </div>
            </div>
            <!-- Search End -->

            <!-- Window Content -->
            <div class="mt-10 display-table width-100 when-window">
                <div class="display-table-row border-b1 background-color-custom-orange">
                    
                    <div class="display-table-cell padding-12">
                        <span class="fs-1rem pretendard-bold">지점</span> 
                    </div>
                    <div class="display-table-cell padding-12">
                        <span class="fs-1rem pretendard-bold">진열</span> 
                    </div>
                    <div class="display-table-cell ta-c padding-12">
                        <span class="fs-1rem pretendard-bold">상품</span> 
                     </div>
                    <div class="display-table-cell ta-c padding-12">
                        <span class="fs-1rem pretendard-bold">단위</span> 
                    </div>
                    <div class="display-table-cell ta-c padding-12">
                        <span class="fs-1rem pretendard-bold">단가</span> 
                    </div>
                    <div class="display-table-cell ta-c padding-12">
                        <span class="fs-1rem pretendard-bold">수수료</span> 
                    </div>
                    <div class="display-table-cell ta-c padding-12">
                        <span class="fs-1rem pretendard-bold">납품</span> 
                    </div>
                    <div class="display-table-cell ta-c padding-12">
                        <span class="fs-1rem pretendard-bold">판매</span> 
                    </div>
                    <div class="display-table-cell ta-c padding-12">
                        <span class="fs-1rem pretendard-bold">회수</span> 
                    </div>
                    <div class="display-table-cell ta-c padding-12">
                        <span class="fs-1rem pretendard-bold">재고</span> 
                    </div>
                    <div class="display-table-cell ta-c padding-12">
                        <span class="fs-1rem pretendard-bold">수익</span> 
                    </div>
                    <div class="display-table-cell ta-c padding-12">
                        <span class="fs-1rem pretendard-bold">변경 사항</span> 
                    </div>
                </div>
                <!-- Body -->
                {#if productDatas.length !== 0}
                    {#each productDatas as data}
                        <div class="display-table-row border-b1">
                            <div class="display-table-cell padding-12">
                                <span class="fs-1rem pretendard-regular">{data.branch_office_nm}</span> 
                            </div>
                            <div class="display-table-cell padding-12">
                                {#if data.product_st === "1"}
                                    <span class="fs-1rem pretendard-regular">기본</span>
                                {:else if data.product_st === "2"}
                                    <span class="fs-1rem pretendard-regular">냉장</span>
                                {:else if data.product_st === "3"}
                                    <span class="fs-1rem pretendard-regular">냉동</span>
                                {/if}
                            </div>
                            <div class="display-table-cell ta-l padding-12">
                                <span class="fs-1rem pretendard-regular">{data.product_nm}</span> 
                            </div>
                            <div class="display-table-cell ta-r padding-12">
                                <span class="fs-1rem pretendard-regular">{data.product_weight} {data.product_weight_dt}</span>
                            </div>
                            <div class="display-table-cell ta-r padding-12">
                                <span class="fs-1rem pretendard-regular">{priceReplace(data.product_price)} 원</span> 
                            </div>
                            <div class="display-table-cell ta-c padding-12">
                                <span class="fs-1rem pretendard-regular">{data.product_commission} %</span> 
                            </div>
                            <div class="display-table-cell ta-r padding-12">
                                <span class="fs-1rem pretendard-regular">{data.product_stock}</span> 
                            </div>
                            <div class="display-table-cell ta-r padding-12">
                                <span class="fs-1rem pretendard-regular">{data.product_sell}</span> 
                            </div>
                            <div class="display-table-cell ta-r padding-12">
                                <span class="fs-1rem pretendard-regular">{data.product_recall}</span> 
                            </div>
                            <div class="display-table-cell ta-r padding-12">
                                {#if (data.product_stock - data.product_sell - data.product_recall) === 0 }
                                    <span class="fs-1rem pretendard-regular">{data.product_stock - data.product_sell - data.product_recall}</span>
                                {:else}
                                    <span class="fs-1rem pretendard-bold" style="color: red;">{data.product_stock - data.product_sell - data.product_recall}</span>
                                {/if}
                            </div>
                            <div class="display-table-cell ta-r padding-12">
                                <span class="fs-1rem pretendard-regular">{ priceReplace(mathRound((data.product_price * data.product_sell) - (((data.product_price * data.product_sell) * data.product_commission)/100)))} 원</span> 
                            </div>
                            <div class="display-table-cell ta-c padding-12">
                                <button type="button" class="fs-1rem pretendard-regular button-update padding-4-8" on:click={()=>{getProductButton(data.product_sq)}}>수정</button>
                                <button type="button" class="fs-1rem pretendard-regular button-delete padding-4-8" on:click={()=>{deleteProductButton(data.product_sq)}}>삭제</button>
                            </div>
                        </div>
                    {/each}
                {/if}
            </div>

            {#if productDatas.length === 0}
                <div class="mt-30 ta-c when-window">
                    <p class="fs-1rem pretendard-regular">등록 된 상품이 없습니다</p>
                    <a href="/product/register" class="fs-1rem pretendard-regular color-tomato" use:link>상품 등록 하기</a>
                </div>
            {/if}
            <!-- Window End -->

            <!-- Mobile Start -->
            <div class="mt-10 when-mobile">
                {#if productDatas.length === 0}
                    <div class="mt-30 ta-c">
                        <p class="fs-1rem pretendard-regular">등록 된 상품이 없습니다</p>
                        <a href="/product/register" class="fs-1rem pretendard-regular color-tomato" use:link>상품 등록 하기</a>
                    </div>
                {:else}
                    {#each productDatas as data}
                        <div class="border-b1 padding-12">
                            <div class="display-flex align-items justify-content-between">
                                <span class="fs-1rem pretendard-regular">{data.branch_office_nm}</span>
                                <span class="fs-1rem pretendard-regular">{data.product_nm}</span> 
                            </div>
                            <div class="mt-10 display-flex align-items justify-content-between">
                                <div class="display-flex align-items gap-10">
                                    {#if data.product_st === "1"}
                                        <span class="fs-1rem pretendard-regular">기본</span>
                                    {:else if data.product_st === "2"}
                                        <span class="fs-1rem pretendard-regular">냉장</span>
                                    {:else if data.product_st === "3"}
                                        <span class="fs-1rem pretendard-regular">냉동</span>
                                    {/if}
                                    <span class="fs-1rem pretendard-regular">{data.product_weight} {data.product_weight_dt}</span>
                                    <span class="fs-1rem pretendard-regular">{data.product_commission} %</span>
                                </div>
                                <div>
                                    <span class="fs-1rem pretendard-regular">{priceReplace(data.product_price)} 원</span> 
                                </div>  
                            </div>
                            
                            <div class="mt-10 display-flex align-items justify-content-between">
                                <div class="display-flex align-items gap-10">
                                    <span class="fs-1rem pretendard-regular">{data.product_stock}</span>
                                    <span class="fs-1rem pretendard-regular">{data.product_sell}</span>
                                    <span class="fs-1rem pretendard-regular">{data.product_recall}</span>
                                    {#if (data.product_stock - data.product_sell - data.product_recall) === 0 }
                                        <span class="fs-1rem pretendard-regular">{data.product_stock - data.product_sell - data.product_recall}</span>
                                    {:else}
                                        <span class="fs-1rem pretendard-bold" style="color: red;">{data.product_stock - data.product_sell - data.product_recall}</span>
                                    {/if}
                                </div>
                                <div>
                                    <span class="fs-1rem pretendard-regular">{ priceReplace( mathRound((data.product_price * data.product_sell) - (((data.product_price * data.product_sell) * data.product_commission)/100)))} 원</span>
                                </div>
                            </div>
                            
                            <div class="mt-20 ta-r">
                                <button type="button" class="fs-1rem pretendard-regular button-update padding-4-8" on:click={()=>{getProductButton(data.product_sq)}}>수정</button>
                                <button type="button" class="fs-1rem pretendard-regular button-delete padding-4-8" on:click={()=>{deleteProductButton(data.product_sq)}}>삭제</button>
                            </div>
                        </div>
                    {/each}
                {/if}
            </div>
            <!-- Mobile End -->

            <div class="mt-20 display-flex align-items justify-content-right gap-10">
                <span class="fs-1rem pretendard-regular">판매 총액 :  <span class="fs-1rem pretendard-regular color-custom-blue">{priceReplace(exCommission)}</span> 원</span>
                <span class="fs-1rem pretendard-regular">|</span>
                <span class="fs-1rem pretendard-regular">수익 총액 :  <span class="fs-1rem pretendard-regular color-custom-blue">{priceReplace(inCommission)}</span> 원</span>
            </div>

            <!-- 페이징 -->
            {#if rowCount >= 10}
                <div class="mt-30 display-flex align-items gap-5">
                    {#if cp !== 1}
                        <div>
                            <button type="button" class="fs-1rem pretendard-regular background-none border-default button-primary padding-4-10" on:click={()=>{getProductList(cp-1)}}>이전</button>
                        </div>
                    {/if}
                    <div>
                        {#each pageDatas as data}
                            {#if data === cp}
                                <button type="button" class="fs-1rem pretendard-regular background-none button-update padding-4-10">{data}</button>    
                            {:else}
                                <button type="button" class="fs-1rem pretendard-regular background-none border-default button-primary padding-4-10" on:click={()=>{getProductList(data)}}>{data}</button>
                            {/if}
                        {/each}
                    </div>
                    {#if cp !== pageCount }
                        <div>
                            <button type="button" class="fs-1rem pretendard-regular background-none border-default button-primary padding-4-10" on:click={()=>{getProductList(cp+1)}}>다음</button>
                        </div>
                    {/if}
                </div>
            {/if}
            <!--  -->

        </div>

    </div>
    <div style="height: 100px;">

    </div>

</div>

<!-- Modal -->
<Modal bind:isModal={isModal}>

    <span slot="modal-title" class="fs-1rem pretendard-bold color-white">상품 수정</span>

    <div slot="modal-content">
        {#await getProduct() then }
            <!-- 지점 선택 -->
        <div class="mt-20">
            <span class="fs-1rem pretendard-bold">지점 선택</span>
            <div class="mt-5">
                <div>
                    {#await getBranchOffice() then }
                        <select bind:value={branchOfficeSq} class="fs-1rem pretendard-regular width-100 border-default border-radius-4 padding-6-12">
                            <option class="fs-1rem pretendard-regular" value="0">지점 선택</option>
                            {#each branchOfficeDatas as data}
                                <option class="fs-1rem pretendard-regular" value={data.branch_office_sq}>{data.branch_office_nm}</option>
                            {/each}
                        </select>
                    {/await}
                </div>
            </div>
        </div>

        <!-- 상품 이름 -->
        <div class="mt-20">
            <span class="fs-1rem pretendard-bold">상품명<span class="color-tomato">*</span></span>
            <div class="mt-5">
                <input type="text" class="fs-1rem pretendard-regular width-100 border-default border-radius-4 padding-8-12" bind:value={productName} placeholder="상품 이름 입력"/>
            </div>
        </div>

        <!-- 기본, 냉장, 냉동 여부 -->
        <div class="mt-20">
            <span class="fs-1rem pretendard-bold">냉장, 냉동 여부<span class="color-tomato">*</span></span>
            <div class="mt-5">
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
            <div class="mt-5">
                <input type="text" class="fs-1rem pretendard-regular width-100 border-default border-radius-4 padding-8-12" bind:value={productPrice} on:input={onlyNumberByPrice} maxlength="20" placeholder="상품 가격 입력(숫자만)"/>
            </div>
        </div>

        <!-- 수수료 -->
        <div class="mt-20">
            <span class="fs-1rem pretendard-bold">수수료 <span class="color-tomato">*</span></span>
            <div class="mt-5">
                <input type="text" class="fs-1rem pretendard-regular width-100 border-default border-radius-4 padding-8-12" bind:value={productCommission} placeholder="수수료 입력"/>
                <p class="mt-5 fs-14 pretendard-regular color-b1">Ex) 11% -> 11, 11.5% -> 11.5, 없음은 0으로 표기</p>
            </div>
        </div>

        <!-- 상품 무게 -->
        <div class="mt-20">
            <span class="fs-1rem pretendard-bold">상품 단위 <span class="color-tomato">*</span></span>
            <div class="mt-5 flex-to-block align-items gap-5">
                <input type="text" class="mt-5 fs-1rem pretendard-regular width-100 border-default border-radius-4 padding-8-12" bind:value={productWeight} on:input={onlyNumberByWeight} placeholder="무게 입력"/>
                <div class="mt-5 width-100">
                    <select bind:value={productWeightDt} class="fs-1rem pretendard-regular width-100 border-default border-radius-4 padding-8-12">
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
            <div class="mt-5">
                <input type="text" class="fs-1rem pretendard-regular border-default border-radius-4 padding-8-12" bind:value={productMeasure} placeholder="단위 입력"/>
                <p class="mt-5 fs-14 pretendard-regular color-b1">Ex) 개, 통, 반통, 봉지, 박스 등</p>
            </div>
        </div> -->

        <!-- 등록 버튼 -->
        <div class="mt-30 ta-c">
            <button type="button" class="fs-1rem pretendard-regular button-primary background-none border-default border-radius-4 padding-8-12" on:click={modifyProductButton}>상품 수정</button>
        </div>
        {/await}
    </div>
</Modal>

<style>
    .search-box {display: flex; align-items: center; gap: 10px;}
    .excel-svg {vertical-align: middle;}
    .excel-btn {border: 1px solid green; transition: background, 0.3s;}
    .excel-btn > span {color: green;}
    .excel-btn:hover {background-color: rgb(6, 129, 6);}
    .excel-btn:hover > svg > g {stroke: #FFF;}
    .excel-btn:hover > span {color: #FFF;}

    .when-mobile {display: none;}
    .flex-to-block {display: flex;}

    @media all and (max-width: 768px){
        .search-box {width: 100%; display: block;}

        .when-480 {width: 100%;}

        .when-window {display: none;}
        .when-mobile {display: block;}
        .flex-to-block {display: block;}
    }
</style>