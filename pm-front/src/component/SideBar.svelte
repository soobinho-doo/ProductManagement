<script lang="ts">
    import { fly } from 'svelte/transition';

    export let show = false;

</script>

{#if show}
    <!-- svelte-ignore a11y-click-events-have-key-events -->
    <!-- svelte-ignore a11y-no-static-element-interactions -->
    <div class="backdrop" on:click={() => {show = false;}}></div>
        <nav class="sidebar-layout" transition:fly={{x: -430, opacity: 1}}>
            <div class="sidebar-content">
                <div class="flex between">
                    <sapn class="fs-20 pretendard-bold">카테고리 메뉴</sapn>
                    <button type="button" class="close-btn" on:click={() => {show = false;}}>
                        <svg xmlns="http://www.w3.org/2000/svg" class="close-svg" width="32px" height="32px" viewBox="0 0 384 512">
                            <path d="M342.6 150.6c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L192 210.7 86.6 105.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3L146.7 256 41.4 361.4c-12.5 12.5-12.5 32.8 0 45.3s32.8 12.5 45.3 0L192 301.3 297.4 406.6c12.5 12.5 32.8 12.5 45.3 0s12.5-32.8 0-45.3L237.3 256 342.6 150.6z"/>
                        </svg>    
                    </button>
                </div>

                <div class="mt-50">
                    <slot name="menu-content"></slot>
                </div>

            </div>
        </nav>
    
{/if}


<style>
    .backdrop {width: 100%; height: 100%; position: fixed; top: 0; left: 0; background: rgba(0, 0, 0, 0.3); z-index: 2;}
    .sidebar-layout {
        position: fixed;
        top: 0;
        left: 0;
        height: 100%;
        border-right:  0.125rem solid #2e2e2e;
        width: 400px;
        z-index: 3;
    }
    .sidebar-content {padding: 24px 12px;}
    .close-btn {padding: 0px; border: none;}

    @media all and (max-width: 450px){
        .sidebar-layout {width: 100%;}
        svg {width: 24px; height: 24px;}
    }
</style>