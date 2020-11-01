<template>
  <header class="header-nav">
    <div class="logo-wrapper">
      <img
        src="/icons/books.svg"
        alt="books"
      >
      <h1>Convey Library</h1>
    </div>

    <div class="nav-wrapper">
      <nav>
        <el-button @click="$emit('openRegisterBook', true)">
          New book
        </el-button>
      </nav>
    </div>

  </header>
</template>

<script src="./scripts.js"></script>

<style lang="scss">
  @import './style.scss';
</style>
