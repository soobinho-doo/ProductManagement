<script lang="ts">
    import SideMenuBar from "../../layout/SideMenuBar.svelte";
    import Header from "../../layout/Header.svelte";
    import Modal from "../../component/Modal.svelte";
    import axios from "axios";
    import { onMount } from "svelte";
    import { link, push } from "svelte-spa-router";
    import { priceReplace } from "../../option/utill";

    onMount(() => {
        getStockList(cp);
        getBranchOfiice();
    })

    // 리스트 Get
    let stockDatas:any = [];
    let stockSt:string = "";
    let today = new Date();
    let year = today.getFullYear();
    let month = String(today.getMonth() + 1).padStart(2, "0");
    let date = String(today.getDate()).padStart(2, "0");
    let oneMonthAgo = new Date(today.setMonth(today.getMonth() - 1));
    let oneMonth = String(oneMonthAgo.getMonth() + 1).padStart(2, "0");
    let oneDay = String(oneMonthAgo.getDate()).padStart(2, "0");
    
    let startDt = oneMonthAgo.getFullYear() + '-'+oneMonth + '-'+oneDay;
    let endDt = year + '-' + month + '-' + date;
    let cp:number; // 첫 페이지 번호
    let rowCount:number = 0;
    let keyword:string = "";
    let branchOfficeNm:string = "";
    let sp:number; // 시작 페이지
    let ep:number; // 마지막 페이지
    let pageCount:number; // 페이지 개수
    let pageDatas:any = []; 
    const getStockList = async (cpNum:any) => {
        if(!cpNum){
            cpNum = 1;
        }
        let data = {
            stock_st:stockSt,
            start_dt:startDt,
            end_dt:endDt,
            cp: cpNum, 
            keyword:keyword, 
            branch_office_nm:branchOfficeNm,
        }
        await axios.post("/api/stock/paging", data).then((res) => {
            //console.log(res.data);
            stockDatas = res.data.list;
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

    // Get BranchOfficeList
    let branchOfficeDatas:any = [];
    const getBranchOfiice = async () => {
        await axios.get("/api/branch-office").then((res)=>{
            branchOfficeDatas = res.data;
        }).catch((err)=>{});
    }

    let stockStDatas:any = [
        {name:"전체", value:""},
        {name:"납품", value:"1"},
        {name:"판매", value:"2"},
        {name:"회수", value:"3"},
    ]

    // 수정
    let isModal:boolean = false;
    let sequence:number;

    let selectProduct:any = [];
    let selectProductSq:string = "";
    let stockState:string = "1";
    let stockNo:number = 0;
    let stockDate:string = year + '-' + month + '-' + date;

    // 납품, 판매, 회수 여부
    let getStockDatas = [
        {label:"납품", value:"1"},
        {label:"판매", value:"2"},
        {label:"회수", value:"3"},
    ]

    // Get
    let getStockSq:number, getStockSt:string, getStockNo:string, getProductSq:string, getStockDt:string;
    const getStockButton = (sq:number, st:string, no:string, productSq:string, stockDt:string) => {
        isModal = !isModal;
        sequence = sq;
        getStockSq = sq;
        getStockSt = st;
        getStockNo = no;
        getProductSq = productSq;
        getStockDt = stockDt;
        //console.log("Before "+sq+" "+st+" "+no+" "+productSq+" "+stockDt+" ")
    }
    
    // Get Stock
    const getStock = async () => {
        await axios.get("/api/stock/"+sequence).then((res)=>{
            stockState = res.data.stock_st;
            selectProductSq = res.data.product_sq;
            stockNo = res.data.stock_no;
            stockDate = res.data.stock_dt;
        }).catch((err)=>{})
    }

    // Get ProductList
    let productDatas:any = [];
    const getProductList = async () => {
        await axios.get("/api/product").then((res)=>{
            productDatas = res.data;
        }).catch((err)=>{});
    }

    const getProduct = async () => {
        await axios.get("/api/product/"+selectProductSq).then((res)=>{
            selectProduct = res.data;
        }).catch((err)=>{});
    }

    // 수정 버튼
    const stockModifyButton = () => {
        //console.log(selectProduct.product_sq)
        if(!selectProduct.product_sq){
            alert("상품을 선택 해 주세요")
        }else if(stockNo === 0){
            alert("개수를 입력 해 주세요")
        }else{
            stockModify();
            //alert("상품이 등록 되셨습니다");
        }
        
    }

    // 수정 RestApi
    const stockModify = async () => {
        let datas = {
            before_stock_st : getStockSt,
            before_product_sq : getProductSq,
            before_stock_no : getStockNo,
            before_stock_dt : getStockDt,
            stock_sq : getStockSq,
            stock_st : stockState,
            product_sq : selectProductSq,
            stock_no : stockNo, 
            stock_dt : stockDate,
        }
        await axios.put("/api/stock", datas).then((res)=>{
            isModal = false;
            getStockList(cp);
        }).catch((err)=>{});
    } 



    // 삭제
    const deleteStockButton = (sq:string, st:string, no:string, productSq:string) => {
        if(confirm("삭제 하시겠습니까?\n삭제 후 데이터는 복구 되지 않습니다")){
            alert("정상적으로 삭제되었습니다.");
            deleteStock(sq, st, no, productSq);
        }else{
            alert("취소 되었습니다");
        }
    }

    const deleteStock = async (sq:string, st:string, no:string, productSq:string) => {
        let datas = {
            stock_sq: sq,
            stock_st: st,
            stock_no: no,
            product_sq: productSq,
        }
        await axios.post("/api/stock/delete", datas).then((res)=>{
            getStockList(cp);
        }).catch((err)=>{});
    }

    // Excel Download
    const stockListByExcel = async () => {
        await axios.get("/api/stock/authentication").then((res)=>{
            location.href="/api/stock/excel/download?user_id="+res.data+"&stock_st="+stockSt+"&start_dt="+startDt+"&end_dt="+endDt+"&keyword="+keyword+"&branch_office_nm="+branchOfficeNm
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
            <p class="fs-20 pretendard-bold">재고 리스트</p>
            <div class="mt-10 display-flex align-items gap-5">
                <span class="fs-14 prtendard-regular">재고 관리</span>
                <span class="fs-14 prtendard-regular">/</span>
                <span class="fs-14 prtendard-regular">재고 리스트</span>
            </div>
        </div>

        <!--  -->
        <div class="mt-20 padding-12 box-shadow-primary background-color-white">
            <!-- 검색 -->
            <div>
                <div class="search-box">
                    <!--  -->
                    <div class="mt-5 display-flex align-items">
                        {#each stockStDatas as data}
                            <button class="{stockSt === data.value ? 'button-update' : ''} fs-1rem pretendard-regular button-primary background-none border-none padding-6-12 when-480" on:click={()=>{stockSt = data.value; cp = 1; getStockList(cp);}}>{data.name}</button>
                        {/each}
                    </div>
                    <!--  -->
                    <div class="mt-5 display-flex align-items gap-10">
                        <input type="date" class="fs-1rem pretendard-regular border-default border-radius-4 padding-6 when-480" bind:value={startDt} on:change={()=>{startDt = startDt; getStockList(cp);}}/>
                        <span class="fs-1rem pretendard-regular">~</span>
                        <input type="date" class="fs-1rem pretendard-regular border-default border-radius-4 padding-6 when-480" bind:value={endDt} on:change={()=>{endDt = endDt; getStockList(cp);}}/>
                    </div>
                </div>
                <!--  -->
                <div class="search-box">
                    <div class="mt-10">
                        <select bind:value={branchOfficeNm} class="fs-1rem pretendard-regular border-default border-radius-4 padding-6-12 when-480" on:change={()=>{cp = 1; getStockList(cp);}}>
                            <option class="fs-1rem pretendard-regular" value="">전체 지점</option>
                            {#each branchOfficeDatas as data}
                                <option class="fs-1rem pretendard-regular" value={data.branch_office_nm}>{data.branch_office_nm}</option>
                            {/each}
                        </select>
                    </div>
                    <div class="mt-10 display-flex align-items">
                        <input type="search" class="fs-16 pretendard-regular border-default border-radius-4 inp-default padding-6 width-100" bind:value={keyword} on:input={()=>{keyword = keyword; cp = 1; getStockList(cp);}} placeholder="상품 및 지점 명 입력"/>
                        <button type="button" class="border-none background-none padding-4" on:click={()=>{getStockList(cp);}}>
                            <svg xmlns="http://www.w3.org/2000/svg" class="svg-color-change" width="18" height="18" viewBox="0 0 512 512">
                                <path d="M416 208c0 45.9-14.9 88.3-40 122.7L502.6 457.4c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L330.7 376c-34.4 25.2-76.8 40-122.7 40C93.1 416 0 322.9 0 208S93.1 0 208 0S416 93.1 416 208zM208 352a144 144 0 1 0 0-288 144 144 0 1 0 0 288z"/>
                            </svg>
                        </button>
                    </div>
                </div>
                
            </div>
            
            
            <!--  -->
            <div class="mt-20 display-flex align-items justify-content-between">
                <span class="fs-1rem pretendard-regular">등록 리스트 {rowCount}</span>
                <div class="display-flex align-items gap-5">
                    <button type="button" class="fs-1rem pretendard-regular button-primary background-none border-default border-radius-4 padding-6-12" on:click={()=>{push("/stock/register")}}>재고 등록</button>
                    <div class="display-flex align-items gap-10">
                        <button type="button" class="display-flex align-items gap-5 excel-btn background-color-white border-radius-4 padding-4-8" on:click={stockListByExcel}>
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
            </div>

            <!-- Window Content -->
            <div class="mt-10 display-table width-100 when-window">
                <div class="display-table-row border-b1 background-color-custom-orange">
                    <div class="display-table-cell ta-c padding-12">
                        <span class="fs-1rem pretendard-bold">구분</span> 
                    </div>
                    <div class="display-table-cell ta-c padding-12">
                        <span class="fs-1rem pretendard-bold">지점</span> 
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
                        <span class="fs-1rem pretendard-bold">진열</span> 
                    </div>
                    <div class="display-table-cell ta-c padding-12">
                        <span class="fs-1rem pretendard-bold">수량</span> 
                    </div>
                    <div class="display-table-cell ta-c padding-12">
                        <span class="fs-1rem pretendard-bold">날짜</span> 
                    </div>
                    <div class="display-table-cell ta-c padding-12">
                        <span class="fs-1rem pretendard-bold">변경 사항</span> 
                     </div>
                </div>
                <!--  -->
                {#if stockDatas.length !== 0}
                    {#each stockDatas as data}
                        <div class="display-table-row border-b1">
                            
                            {#if data.stock_st === "1"}
                                <div class="display-table-cell ta-c padding-12" style="background-color: #c6dfec;">
                                    <span class="fs-1rem pretendard-bold">납품</span>
                                </div>
                                    
                                {:else if  data.stock_st === "2"}
                                <div class="display-table-cell ta-c padding-12" style="background-color: #ffb6a9;">
                                    <span class="fs-1rem pretendard-bold">판매</span>
                                </div>
                                    
                                {:else if  data.stock_st === "3"}
                                <div class="display-table-cell ta-c padding-12" style="background-color: #caffca;">
                                    <span class="fs-1rem pretendard-bold">회수</span>
                                </div>
                            {/if}
                            
                            <div class="display-table-cell padding-12">
                                <span class="fs-1rem pretendard-regular">{data.branch_office_nm}</span> 
                            </div>
                            <div class="display-table-cell padding-12">
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
                            <div class="display-table-cell ta-c padding-12">
                                {#if data.product_st === "1"}
                                    <span class="fs-1rem pretendard-regular">기본</span>
                                {:else if data.product_st === "2"}
                                    <span class="fs-1rem pretendard-regular">냉장</span>
                                {:else if data.product_st === "3"}
                                    <span class="fs-1rem pretendard-regular">냉동</span>
                                {/if} 
                            </div>
                            <div class="display-table-cell ta-r padding-12">
                                <span class="fs-1rem pretendard-regular">{data.stock_no}</span> 
                            </div>
                            <div class="display-table-cell ta-c padding-12">
                                <span class="fs-1rem pretendard-regular">{data.stock_dt}</span> 
                            </div>
                            <div class="display-table-cell ta-c padding-12">
                                <button type="button" class="fs-1rem pretendard-regular button-update padding-4-8" on:click={()=>{getStockButton(data.stock_sq, data.stock_st, data.stock_no, data.product_sq, data.stock_dt)}}>수정</button>
                                <button type="button" class="fs-1rem pretendard-regular button-delete padding-4-8" on:click={()=>{deleteStockButton(data.stock_sq, data.stock_st, data.stock_no, data.product_sq)}}>삭제</button>
                            </div>
                        </div>                    
                    {/each}
                {/if}
            </div>

            {#if stockDatas.length === 0}
                <div class="mt-30 ta-c when-window">
                    <p class="fs-1rem pretendard-regular">등록 된 재고가 없습니다</p>
                    <a href="/stock/register" class="fs-1rem pretendard-regular color-tomato" use:link>재고 등록 하기</a>
                </div>
            {/if}
            <!-- Window End -->

            <!-- Mobile start -->
            <div class="mt-10 when-mobile">
                {#if stockDatas.length === 0}
                    <div class="mt-30 ta-c">
                        <p class="fs-1rem pretendard-regular">등록 된 재고가 없습니다</p>
                        <a href="/stock/register" class="fs-1rem pretendard-regular color-tomato" use:link>재고 등록 하기</a>
                    </div>
                {:else}
                    {#each stockDatas as data}
                        <div class="border-b1 padding-12">
                            <div class="mt-10">
                                {#if data.stock_st === "1"}
                                    <div>
                                        <span class="fs-1rem pretendard-bold" style="color: #2198d5;">납품</span>
                                    </div>  
                                {:else if  data.stock_st === "2"}
                                    <div>
                                        <span class="fs-1rem pretendard-bold" style="color: tomato;">판매</span>
                                    </div>
                                {:else if  data.stock_st === "3"}
                                    <div>
                                        <span class="fs-1rem pretendard-bold" style="color: green;">회수</span>
                                    </div>
                                {/if} 
                            </div>
                            <div class="mt-10 display-flex align-items justify-content-between">
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
                                <span class="fs-1rem pretendard-regular">{data.stock_no} 개</span>
                                <span class="fs-1rem pretendard-regular">{data.stock_dt}</span> 
                            </div>
                                
                            <div class="mt-20 ta-r">
                                <button type="button" class="fs-1rem pretendard-regular button-update padding-4-8" on:click={()=>{getStockButton(data.stock_sq, data.stock_st, data.stock_no, data.product_sq, data.stock_dt)}}>수정</button>
                                <button type="button" class="fs-1rem pretendard-regular button-delete padding-4-8" on:click={()=>{deleteStockButton(data.stock_sq, data.stock_st, data.stock_no, data.product_sq)}}>삭제</button>
                            </div>
                        </div>
                    {/each}
                {/if}
            </div>
            <!-- Mobile End -->

            <!-- 페이징 -->
            {#if rowCount >= 10}
                <div class="mt-30 display-flex align-items gap-5">
                    {#if cp !== 1}
                        <div>
                            <button type="button" class="fs-1rem pretendard-regular background-none border-default button-primary padding-4-10" on:click={()=>{getStockList(cp-1)}}>이전</button>
                        </div>
                    {/if}
                    
                    <div>
                        {#each pageDatas as data}
                            {#if data === cp}
                                <button type="button" class="fs-1rem pretendard-regular background-none button-update padding-4-10">{data}</button>    
                            {:else}
                                <button type="button" class="fs-1rem pretendard-regular background-none border-default button-primary padding-4-10" on:click={()=>{getStockList(data)}}>{data}</button>
                            {/if}
                        {/each}
                    </div>

                    {#if cp !== pageCount }
                        <div>
                            <button type="button" class="fs-1rem pretendard-regular background-none border-default button-primary padding-4-10" on:click={()=>{getStockList(cp+1)}}>다음</button>
                        </div>
                    {/if}
                </div>   
            {/if}

        </div>

    </div>

    <div style="height: 100px;">

    </div>

</div>

<!-- Modal -->
<Modal bind:isModal={isModal}>
    <span slot="modal-title" class="fs-1rem pretendard-bold color-white">재고 수정</span>

    <div slot="modal-content">
        {#await getStock() then }
            <!-- 납품, 판매, 회수 여부 -->
            <div>
                <span class="fs-1rem pretendard-bold">납품 • 판매 • 회수 여부 <span class="color-tomato">*</span></span>
                <div class="mt-10">
                    {#each getStockDatas as data}
                        <label class="fs-1rem pretendard-regular padding-right-12">
                            <input type="radio" bind:group={stockState} value={data.value} />
                            {data.label}
                        </label>
                    {/each}
                </div>
            </div>

            <!-- 상품 선택 -->
            <div class="mt-20">
                <span class="fs-1rem pretendard-bold">상품 선택 <span class="color-tomato">*</span></span>
                <div class="mt-10">
                    <div>
                        {#await getProductList() then }
                            <select bind:value={selectProductSq} class="fs-1rem pretendard-regular border-default border-radius-4 padding-6-12" on:change={getProduct}>
                                {#each productDatas as data}
                                    <option class="fs-1rem pretendard-regular" value={data.product_sq}>{data.product_nm}</option>
                                {/each}
                            </select>
                        {/await}
                    </div>
                    <div>
                        
                        {#await getProduct() then }
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
                                            <span class="fs-1rem pretendard-regular">기본 제품</span>
                                        {:else if selectProduct.product_st === "2"}
                                            <span class="fs-1rem pretendard-regular">냉장 제품</span>
                                        {:else if selectProduct.product_st === "3"}
                                            <span class="fs-1rem pretendard-regular">냉동 제품</span>
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
                            {/await}
                        
                        
                    </div>
                </div>
            </div>

            <!-- 개수 -->
            <div class="mt-20">
                <span class="fs-1rem pretendard-bold">개수 <span class="color-tomato">*</span></span>
                <div class="mt-10">
                    <input type="number" class="fs-1rem pretendard-regular border-default border-radius-4 padding-8-12" bind:value={stockNo}/>
                </div>
            </div>

            <!-- 날짜 -->
            <div class="mt-20">
                <span class="fs-1rem pretendard-bold">납품, 판매, 회수 당일 날짜 <span class="color-tomato">*</span></span>
                <div class="mt-10">
                    <input type="date" class="fs-1rem pretendard-regular border-default border-radius-4 padding-8-12" bind:value={stockDate}/>
                </div>
            </div>

            <!-- 수정 버튼 -->
            <div class="mt-30 ta-c">
                <button type="button" class="fs-1rem pretendard-regular button-primary background-none border-default border-radius-4 padding-8-12" on:click={stockModifyButton}>재고 수정</button>
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

    @media all and (max-width: 768px){
        .search-box {width: 100%; display: block;}

        .when-480 {width: 100%;}

        .when-window {display: none;}
        .when-mobile {display: block;}
    }
</style>