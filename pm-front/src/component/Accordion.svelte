<script lang="ts"> 
    import { slide } from "svelte/transition";

    let isAccordionOpen:boolean = false;

</script>

<div class="toggle-div">
    <button type="button" class="toggle-btn background-none" on:click={() => {isAccordionOpen = !isAccordionOpen}}>
        <slot name="acc-title"></slot>
    </button>
</div>
{#if isAccordionOpen}
    <div transition:slide={{ duration: 200 }}>
        <slot name="acc-content"></slot>
    </div>
{/if}


<style>
    .toggle-btn {width: 100%; text-align: left; border: none;}
</style>