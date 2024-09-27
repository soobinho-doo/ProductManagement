// 가격 3자리마다 , 찍기
export const priceReplace = (price:any) => { 
    return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

// 소수점 반올림, 반올림
export const mathRound = (price:number) => {
    return Math.round(price)
}

// 소수점 무조건 반올림
export const mathCeil = (price:number) => {
    return Math.ceil(price);
}

// 숫자만 입력 가능
export const onlyNumber = (value:string) => {
    return value.replace(/[^0-9]/g, '').replace(/(\..*)\./g, '$1');
}

// 글자 안돼 
export const notOnlyLetter = (value:string) => {
    return value.replace(/[ㄱ-ㅎ|가-힣|a-z|A-Z]/g, '').replace(/(\..*)\./g, '$1');
}

// 상품 관련
// 
// 냉장 여부
export let keepDatas = [
    {label:"기본", value:"1", id:"one"},
    {label:"냉장", value:"2", id:"two"},
    {label:"냉동", value:"3", id:"three"},
]

// 무게 단위 표시
export let weightDatas = [
    "mg", "g", "kg", "t", "ml", "l", "통", "팩", "봉지", "반통", "개", "망", "박스"
]

// 납품, 판매, 회수 여부
export let stockStList = [
    {label:"납품", value:"1"},
    {label:"판매", value:"2"},
    {label:"회수", value:"3"},
]

export let stockStDatas:any = [
    {name:"전체", value:""},
    {name:"납품", value:"1"},
    {name:"판매", value:"2"},
    {name:"회수", value:"3"},
]

// page size 
export let pageSizeDatas:any = [
    10, 20, 30, 50, 100
]