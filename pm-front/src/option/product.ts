import { get } from "svelte/store";
import { delApi, getApi, patchApi, postApi } from "./api";
import { auth } from "./auth";
import { noti } from "./store";
import { push } from "svelte-spa-router";

function setProduct () {

    //
    const list = async (data:any) => {
        const accessToken = get(auth).Authorization;
        try{
            const options = {
                path : '/api/product/paging', 
                data : data,
                accessToken : accessToken,
            }
            const productList:any = await postApi(options)
            return productList

        }catch(err){
            throw err
        }
    }

    //
    const info = async (sq:number) => {
        const accessToken = get(auth).Authorization;
        try{
            const options = {
                path : "/api/product/"+sq, 
                accessToken : accessToken,
            }
            const productInfo = await getApi(options);
            return productInfo
        }catch(err){
            throw err
        }
    }

    //
    const input = async (data:any) => {
        const accessToken = get(auth).Authorization;
        try{
            const options = {
                path : '/api/product', 
                data : data, 
                accessToken : accessToken,
            }
            const productInput = await postApi(options);
            if(productInput === 1){
                push("/product/list")
                noti.success("상품 등록 완료", 2000)
            }
        }catch(err){
            throw err
        }
    }

    //
    const modify = async (sq:number, data:any) => {
        const accessToken = get(auth).Authorization;
        try{
            const options = {
                path : "/api/product/"+sq, 
                data : data, 
                accessToken : accessToken,
            }
            const productModify = await patchApi(options)
            return productModify
            
        }catch(err){
            throw err
        }
    }

    //
    const del = async (sq:number) => {
        const accessToken = get(auth).Authorization;
        try{
            const options = {
                path : "/api/product/"+sq, 
                accessToken : accessToken,
            }
            const productDel = await delApi(options)
            return productDel

        }catch(err){
            throw err
        }
    }

    return {
        list,
        info,
        input,
        modify,
        del,
    }
}

export const product = setProduct();