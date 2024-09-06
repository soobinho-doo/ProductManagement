import { get } from "svelte/store";
import { delApi, getApi, patchApi, postApi, putApi } from "./api";
import { auth } from "./auth";

function setDashBoard () {

    const stockList = async (branchName:string, productSq:number) => {
        const accessToken = get(auth).Authorization;

        try{
            const options = {
                path: '/api/product/dashboard',
                data: {
                    branchName: branchName, 
                    productSq: productSq
                },
                accessToken:accessToken
            }
            const getStockList:any = await postApi(options);
            return getStockList;

        }catch(err){
            throw err
        }
    }

    return {    
        stockList,
    }
}

export const dashBaord = setDashBoard();