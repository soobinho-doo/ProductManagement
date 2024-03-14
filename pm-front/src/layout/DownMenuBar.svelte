<script lang="ts">
    import { push } from "svelte-spa-router";
    import { isLogin } from "../option/auth";
    
</script>

<div class="down-menu-bar background-color-white border-top-b1 width-100">
    <div class="display-flex align-items">
        <!-- 메뉴 버튼 -->
        <button type="button" class="background-none border-none width-100" on:click={() => {push("/menu")}}>
            <svg xmlns="http://www.w3.org/2000/svg" class="svg-color-change" width="24" height="24" viewBox="0 0 512 512">
                <path d="M0 96C0 78.3 14.3 64 32 64H416c17.7 0 32 14.3 32 32s-14.3 32-32 32H32C14.3 128 0 113.7 0 96zM64 256c0-17.7 14.3-32 32-32H480c17.7 0 32 14.3 32 32s-14.3 32-32 32H96c-17.7 0-32-14.3-32-32zM448 416c0 17.7-14.3 32-32 32H32c-17.7 0-32-14.3-32-32s14.3-32 32-32H416c17.7 0 32 14.3 32 32z"/>
            </svg>
        </button>

        <!-- 검색 버튼 -->
        <button type="button" class="display-grid background-none border-none width-100" on:click={() => {push("/salesstatus")}}>
            <span class="fs-1rem pretendard-bold">판매</span>
            <span class="fs-1rem pretendard-bold">현황</span>
        </button>

        <!-- 홈 버튼 -->
        <button type="button" class="background-none border-none width-100" on:click={() => {push("/")}}>
            <svg xmlns="http://www.w3.org/2000/svg" class="svg-color-change" width="32" height="32" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1" d="M20 19v-8.5a1 1 0 0 0-.4-.8l-7-5.25a1 1 0 0 0-1.2 0l-7 5.25a1 1 0 0 0-.4.8V19a1 1 0 0 0 1 1h4a1 1 0 0 0 1-1v-3a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v3a1 1 0 0 0 1 1h4a1 1 0 0 0 1-1z"/>
            </svg>
        </button>

        <!-- 로그인 버튼 -->
        {#if $isLogin}
            <button type="button" class="background-none border-none width-100" on:click={() => {push("/mypage")}}>
                <svg xmlns="http://www.w3.org/2000/svg" class="svg-color-change" width="24" height="24" viewBox="0 0 24 24">
                    <path d="M12 1L3 5V11C3 16.55 6.84 21.74 12 23C17.16 21.74 21 16.55 21 11V5L12 1ZM12 5C12.7956 5 13.5587 5.31607 14.1213 5.87868C14.6839 6.44129 15 7.20435 15 8C15 8.79565 14.6839 9.55871 14.1213 10.1213C13.5587 10.6839 12.7956 11 12 11C11.2044 11 10.4413 10.6839 9.87868 10.1213C9.31607 9.55871 9 8.79565 9 8C9 7.20435 9.31607 6.44129 9.87868 5.87868C10.4413 5.31607 11.2044 5 12 5ZM17.13 17C15.9184 18.8547 14.1078 20.2382 12 20.92C9.89217 20.2382 8.08162 18.8547 6.87 17C6.53 16.5 6.24 16 6 15.47C6 13.82 8.71 12.47 12 12.47C15.29 12.47 18 13.79 18 15.47C17.76 16 17.47 16.5 17.13 17Z"/>
                </svg>
            </button>
        {:else}
            <button type="button" class="background-none border-none width-100" on:click={() => {push("/login")}}>
                <svg xmlns="http://www.w3.org/2000/svg" class="svg-color-change" width="24" height="24" viewBox="0 0 448 512">
                    <path d="M224 256A128 128 0 1 0 224 0a128 128 0 1 0 0 256zm-45.7 48C79.8 304 0 383.8 0 482.3C0 498.7 13.3 512 29.7 512H418.3c16.4 0 29.7-13.3 29.7-29.7C448 383.8 368.2 304 269.7 304H178.3z"/>
                </svg>
            </button>
        {/if}
        

        <!-- 설정 버튼 -->
        <button type="button" class="background-none border-none width-100" on:click={() => {push("/setting")}}>
            <svg xmlns="http://www.w3.org/2000/svg" class="svg-color-change" width="32" height="32" viewBox="0 0 24 24">
                <path d="m10.135 21l-.362-2.892q-.479-.145-1.035-.454q-.557-.31-.948-.664l-2.667 1.135l-1.865-3.25l2.305-1.738q-.044-.272-.073-.56q-.028-.287-.028-.558q0-.252.028-.53t.073-.626L3.258 9.125l1.865-3.212L7.771 7.03q.448-.373.97-.673q.52-.3 1.013-.464L10.134 3h3.731l.362 2.912q.575.201 1.016.463q.442.262.909.654l2.725-1.116l1.865 3.212l-2.382 1.796q.082.31.092.568q.01.26.01.511q0 .233-.02.491q-.019.259-.088.626l2.344 1.758l-1.865 3.25l-2.681-1.154q-.467.392-.94.673q-.474.281-.985.444L13.865 21h-3.73ZM11 20h1.956l.369-2.708q.756-.2 1.36-.549q.605-.349 1.232-.956l2.495 1.063l.994-1.7l-2.189-1.644q.125-.427.166-.786q.04-.358.04-.72q0-.38-.04-.72q-.04-.34-.166-.747l2.227-1.683l-.994-1.7l-2.552 1.07q-.454-.499-1.193-.934t-1.4-.578L13 4h-1.994l-.312 2.688q-.756.162-1.39.52q-.633.36-1.26.986L5.55 7.15l-.994 1.7l2.169 1.62q-.125.336-.175.73q-.05.394-.05.82q0 .38.05.755t.156.73l-2.15 1.645l.994 1.7l2.475-1.05q.588.594 1.222.953t1.428.559L11 20Zm.973-5.5q1.046 0 1.773-.727q.727-.727.727-1.773q0-1.046-.727-1.773q-.727-.727-1.773-.727q-1.052 0-1.776.727T9.473 12q0 1.046.724 1.773q.724.727 1.776.727ZM12 12Z"/>
            </svg>
        </button>
    </div>
</div>

<style>
    .down-menu-bar {display: none; padding: 8px 0;}
    

    @media all and (max-width: 768px){
        .down-menu-bar {display: block; position: fixed; bottom: 0; height: 50px;}
    }
</style>