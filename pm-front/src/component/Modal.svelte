<script lang="ts">
    import { fade } from 'svelte/transition';

    export let isModal = false;
    const closeModal = () => {
        isModal = false;
    }

</script>

{#if isModal}
    <!-- svelte-ignore a11y-click-events-have-key-events -->
    <!-- svelte-ignore a11y-no-static-element-interactions -->
    <div class="backdrop" on:click={closeModal}>
    </div>

    <div class="modal border-radius-4" transition:fade={{ delay: 50, duration: 150 }}>
        <!-- 타이틀 -->
        <div class="display-flex align-items justify-content-between modal-title">
            <slot name="modal-title"></slot>
            <button type="button" class="fs-16 pretendard-regular background-none border-none" on:click={closeModal}>
                <svg xmlns="http://www.w3.org/2000/svg" fill="#FFF" width="20" height="20" viewBox="0 0 16 16">
                    <path d="M4.28 3.22a.75.75 0 0 0-1.06 1.06L6.94 8l-3.72 3.72a.75.75 0 1 0 1.06 1.06L8 9.06l3.72 3.72a.75.75 0 1 0 1.06-1.06L9.06 8l3.72-3.72a.75.75 0 0 0-1.06-1.06L8 6.94L4.28 3.22Z" clip-rule="evenodd"/>
                </svg>
            </button>
        </div>

        <!-- 컨텐츠 -->
        <div class="modal-content background-color-white">
            <slot name="modal-content"></slot>
        </div>
    </div>

{/if}



<style>
    .backdrop {width: 100%; height: 100%; position: fixed; top: 0; left: 0; background: rgba(0, 0, 0, 0.6); z-index: 5;}
    .modal { max-width: 400px; min-width: 400px; position:absolute; top:50%; left:50%; transform:translate(-50%,-50%); z-index: 10;}
    
    .modal-title {background: #2A3F54; padding: 8px 12px; border-top-right-radius: 4px; border-top-left-radius: 4px;}
    .modal-content {max-height: 600px; overflow-y: auto; padding: 16px 10px; border-bottom-left-radius: 4px; border-bottom-right-radius: 4px;}
    .modal-content::-webkit-scrollbar { width: 10px;}
    .modal-content::-webkit-scrollbar-thumb { background-color: #909090; border-radius: 2px;}
    .modal-content::-webkit-scrollbar-track { background-color: transparent; }
    svg {vertical-align: middle;}

    @media all and (max-width: 480px){
        .modal {min-width: 96%;}
    }

  </style>