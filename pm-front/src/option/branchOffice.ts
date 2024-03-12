import { get, writable } from "svelte/store";
import { delApi, getApi, patchApi, postApi } from "./api";
import { auth } from "./auth";
import { push } from "svelte-spa-router";
import { noti } from "./store";

function setBranchOffice () {
    //
    const list = async (data:any) => {
        const accessToken = get(auth).Authorization;
        try{
            const options = {
                path : '/api/branch-office/paging', 
                data : data, 
                accessToken : accessToken
            }
            const branchOfficeList:any = await postApi(options);
            return branchOfficeList

        }catch(err){
            throw err
        }
    }

    //
    const info = async (sequence:number) => {
        const accessToken = get(auth).Authorization;
        
        try{
            const options = {
                path : "/api/branch-office/"+sequence, 
                accessToken : accessToken,
            }
            const branchOfficeInfo = await getApi(options)
            return branchOfficeInfo

        }catch(err){
            throw err
        }
    }

    //
    const input = async (data:any) => {
        const accessToken = get(auth).Authorization;
        try{
            const options = {
                path : '/api/branch-office', 
                data : data, 
                accessToken : accessToken,
            }
            const branchOfficeInput = await postApi(options);
            if(branchOfficeInput === 1){
                push("/branchoffice/list")
                noti.success("지점 등록 완료", 2000)
            }
            
        }catch(err){
            throw err
        }
    }
    //
    const modify = async (sequence:number, data:any) => {
        const accessToken = get(auth).Authorization;
        try{
            const options = {
                path : "/api/branch-office/"+sequence, 
                data : data, 
                accessToken : accessToken,
            }
            const branchOfficeModify = await patchApi(options);
            return branchOfficeModify
  
        }catch(err){
            throw err
        }
    }
    const del = async (sequence:number) => {
        const accessToken = get(auth).Authorization;
        try{
            const options = {
                path : "/api/branch-office/"+sequence, 
                accessToken : accessToken,
            }
            const branchOfficeDel = await delApi(options);
            return branchOfficeDel
  
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

export const branchOffice = setBranchOffice(); 