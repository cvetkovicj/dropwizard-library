import axios from 'axios'
import ApiEndpoint from '../helper/apiEndpoint'

export default {

  getSixBooks () {
    return axios({
      method: 'get',
      url: ApiEndpoint.BOOK_API,
      headers: {
        // 'Content-Type': 'application/json'
      }
    })
  },
  editBook (id, book) {
    return axios({
      method: 'put',
      url: `${ApiEndpoint.BOOK_API}/${id}`,
      data: book,
      headers: {
        'Content-Type': 'application/json'
      }
    })
  },
  addBook (book) {
    return axios({
      method: 'post',
      url: `${ApiEndpoint.BOOK_API}/add`,
      data: book,
      headers: {
        'Content-Type': 'application/json'
      }
    })
  },
  deleteBook (id) {
    return axios({
      method: 'delete',
      url: `${ApiEndpoint.BOOK_API}/${id}`,
      headers: {
        'Content-Type': 'application/json'
      }
    })
  },
  findBooks (searchText) {
    return axios({
      method: 'get',
      url: `${ApiEndpoint.BOOK_API}/search`,
      params: {
        "searchText": `${searchText}`
      },
      headers: {
        'Content-Type': 'application/json'
      }
    })
  }


}
