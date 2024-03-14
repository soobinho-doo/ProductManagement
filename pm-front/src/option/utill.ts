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
    return value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
}

// 상품 관련
// 
// 냉장 여부
export let keepDatas = [
    {label:"기본", value:"1", id:"one"},
    {label:"냉장", value:"2", id:"two"},
    {label:"냉동", value:"3", id:"three"},
]

// 무게 표시
export let weightDatas = [
    "mg", "g", "kg", "t", "ml", "l", "통", "팩", "봉지", "반통", "개"
]