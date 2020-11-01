<template>
  <section class="home">
    <HeaderNav @openRegisterBook="(val) => { modalOptions.isRegister = val }" />

    <div class="search-wrapper">
      <input
        class="el-input__inner"
        v-model="searchText"
        maxlength="50"
        placeholder="Search title or author.."
        clearable
        @keyup.enter="findBooks()">
    </div>

    <ul class="books-list">
      <li
        v-for="(book, index) in getterSixBooks"
        :key="book.id"
      >
        <img
          :src="index <= 4 ? `/icons/books/book-${index}.svg` : `/icons/books/book-${index - 4}.svg`"
          alt="book"
        >

        <div class="book-description">
          <span class="book-isbn">{{ book.isbn }}</span>

          <span class="book-name">{{ book.title }}</span>

          <span class="book-author">{{ book.listOfAuthors }}</span>

          <span class="book-genre">{{ book.genre }}</span>

          <span class="book-pages">{{ book.numberOfPages }} pages</span>
        </div>

        <div class="manager-book">
          <el-button
            round
            icon="el-icon-edit"
            @click="openEditingModal(book)"
          >
            Edit
          </el-button>

          <el-button
            round
            icon="el-icon-delete"
            @click="confirmDeleteBook(book.id)"
          >
            Delete
          </el-button>
        </div>
      </li>
    </ul>

    <div
      v-if="getterSixBooks.length <= 0"
      class="no-book"
    >
      <img
        src="/icons/no-book.svg"
        alt="no book"
      >

      <span>There are no books registered...</span>
    </div>

    <ModalManagerBook
      :is-open-modal="modalOptions.isEditing || modalOptions.isRegister"
      :modal-options="modalOptions"
      @closeManagerBookModal="() => closeModal()"
    />
  </section>
</template>

<script src="./scripts.js"></script>

<style lang="scss">
  @import './style.scss';
</style>
