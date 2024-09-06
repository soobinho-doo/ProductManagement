<script lang="ts">
    import Accordion from "../component/Accordion.svelte";
    import axios from "axios";
    import { onMount } from "svelte";
    import { link, push } from "svelte-spa-router";

    // 변수
    let menuDatas:any = []

    // Method
    onMount(() => {
        getMenus();
    })

    const getMenus = async () => {
        await axios.get("/api/menu").then((res)=>{
            menuDatas = res.data;
        }).catch((err)=>{});
    }

</script>


<div class="side-menu-bar border-none border-right-b1">
    <div class="height-50px border-bottom-b1 brand">
        <a class="fs-18 pretendard-bold color-white" href="/" use:link>Product Management</a>
    </div>

    <div class="border-top-black">
        {#each menuDatas as datas}
            <Accordion>
                <div class="display-flex align-items gap-10 padding-12 border-bottom-b1 select-menu-bar" slot="acc-title">
                    {#if datas.menu_cd === "M001"}
                        <svg xmlns="http://www.w3.org/2000/svg" class="side-svg-fill-white" width="24" height="24" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1" d="M20 19v-8.5a1 1 0 0 0-.4-.8l-7-5.25a1 1 0 0 0-1.2 0l-7 5.25a1 1 0 0 0-.4.8V19a1 1 0 0 0 1 1h4a1 1 0 0 0 1-1v-3a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v3a1 1 0 0 0 1 1h4a1 1 0 0 0 1-1z"/>
                        </svg>
                    {:else if datas.menu_cd === "M002"}
                        <svg xmlns="http://www.w3.org/2000/svg" class="side-svg-fill-white" width="24" height="24" viewBox="0 0 48 48">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="4" d="M24 33V15M10 9h28v6H10zM8 32l6-7h19.974L40 32M4 33h8v8H4zm16 0h8v8h-8zm16 0h8v8h-8z"/>
                        </svg>
                    {:else if datas.menu_cd === "M003"}
                        <svg xmlns="http://www.w3.org/2000/svg" class="side-svg-fill-white" width="24" height="24" viewBox="0 0 24 24">
                            <path d="M10.591 2.513a3.75 3.75 0 0 1 2.818 0l7.498 3.04A1.75 1.75 0 0 1 22 7.175v9.653a1.75 1.75 0 0 1-1.093 1.621l-7.498 3.04a3.75 3.75 0 0 1-2.818 0l-7.498-3.04A1.75 1.75 0 0 1 2 16.827V7.176a1.75 1.75 0 0 1 1.093-1.622l7.498-3.04Zm2.254 1.39a2.25 2.25 0 0 0-1.69 0L9.24 4.68l7.527 2.927l2.669-1.03l-6.592-2.673Zm1.846 4.505L7.215 5.5L4.59 6.564l7.411 2.882l2.69-1.038ZM3.5 16.828a.25.25 0 0 0 .156.231l7.499 3.04c.031.013.063.025.095.036v-9.371L3.5 7.75v9.078Zm9.345 3.271l7.499-3.04a.25.25 0 0 0 .156-.232V7.774l-7.75 2.992v9.37a2.18 2.18 0 0 0 .095-.036Z"/>
                        </svg>
                    {:else if datas.menu_cd === "M004"}
                        <svg xmlns="http://www.w3.org/2000/svg" class="side-svg-fill-white" width="24" height="24" viewBox="0 0 24 24">
                            <path d="M5 4h14a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2m0 4v4h6V8H5m8 0v4h6V8h-6m-8 6v4h6v-4H5m8 0v4h6v-4h-6Z"/>
                        </svg>
                    {/if}
                    <span class="fs-18 pretendard-bold color-white">{datas.menu_nm}</span>
                </div>
                <div slot="acc-content">
                    {#each datas.subMenus as data}
                        <button type="button" class="display-flex align-items gap-5 padding-8-16 width-100 background-none border-none select-menu-bar" on:click={()=>{push(data.menu_srs)}}>
                            <svg xmlns="http://www.w3.org/2000/svg" class="side-svg-fill" width="18" height="18" viewBox="0 0 15 15">
                                <path d="M4 7.5a3.5 3.5 0 0 1 3-3.465V0h1v4.035a3.5 3.5 0 0 1 0 6.93V15H7v-4.035A3.5 3.5 0 0 1 4 7.5Z"/>
                            </svg>
                            <span class="fs-16 pretendard-regular color-white">{data.menu_nm}</span>
                        </button>
                    {/each}
                </div>
            </Accordion>
        {/each}
    </div>
</div>



<style>
    .brand {padding: 14px 12px 14px 12px;}
    @media all and (max-width: 480px){
        
    }
</style>