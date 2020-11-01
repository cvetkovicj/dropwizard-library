<template>
  <el-dialog
    :title="modalTitle"
    :visible.sync="isOpenModal"
    :before-close="() => { $emit('closeManagerBookModal') }"
  >
    <el-input
      v-model="isbn"
      placeholder="ISBN"
      maxlength="10"
      clearable
    />

    <el-input
      v-model="title"
      placeholder="Title"
      maxlength="50"
      clearable
    />

    <el-input
      v-model="listOfAuthors"
      placeholder="Author"
      maxlength="50"
      clearable
    />

    <el-input
      v-model="genre"
      maxlength="30"
      placeholder="Genre (novel, travel...)"
      clearable
    />

    <el-input
      v-model="numberOfPages"
      placeholder="Number of pages"
      type="number"
      clearable
    >
    </el-input>



    <div class="action-buttons">
      <el-button
        :loading="inRequest"
        round
        type="primary"
        @click="saveModalData()"
      >
        Save
      </el-button>

      <el-button
        :disabled="inRequest"
        type="danger"
        round
        @click="$emit('closeManagerBookModal')"
      >
        Cancel
      </el-button>
    </div>
  </el-dialog>
</template>

<script src="./scripts.js"></script>

<style lang="scss">
  @import './style.scss';
</style>
