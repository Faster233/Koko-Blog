<template>
  <el-dialog
    :title="!dataForm.blogId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="博客标题" prop="blogTitle">
      <el-input v-model="dataForm.blogTitle" placeholder="博客标题"></el-input>
    </el-form-item>
    <el-form-item label="博客自定义路径url" prop="blogSubUrl">
      <el-input v-model="dataForm.blogSubUrl" placeholder="博客自定义路径url"></el-input>
    </el-form-item>
    <el-form-item label="博客封面图" prop="blogCoverImage">
      <el-input v-model="dataForm.blogCoverImage" placeholder="博客封面图"></el-input>
    </el-form-item>
    <el-form-item label="博客内容" prop="blogContent">
      <el-input v-model="dataForm.blogContent" placeholder="博客内容"></el-input>
    </el-form-item>
    <el-form-item label="博客分类id" prop="blogCategoryId">
      <el-input v-model="dataForm.blogCategoryId" placeholder="博客分类id"></el-input>
    </el-form-item>
    <el-form-item label="博客分类(冗余字段)" prop="blogCategoryName">
      <el-input v-model="dataForm.blogCategoryName" placeholder="博客分类(冗余字段)"></el-input>
    </el-form-item>
    <el-form-item label="博客标签" prop="blogTags">
      <el-input v-model="dataForm.blogTags" placeholder="博客标签"></el-input>
    </el-form-item>
    <el-form-item label="0-草稿 1-发布" prop="blogStatus">
      <el-input v-model="dataForm.blogStatus" placeholder="0-草稿 1-发布"></el-input>
    </el-form-item>
    <el-form-item label="阅读量" prop="blogViews">
      <el-input v-model="dataForm.blogViews" placeholder="阅读量"></el-input>
    </el-form-item>
    <el-form-item label="0-允许评论 1-不允许评论" prop="enableComment">
      <el-input v-model="dataForm.enableComment" placeholder="0-允许评论 1-不允许评论"></el-input>
    </el-form-item>
    <el-form-item label="是否删除 0=否 1=是" prop="isDeleted">
      <el-input v-model="dataForm.isDeleted" placeholder="是否删除 0=否 1=是"></el-input>
    </el-form-item>
    <el-form-item label="添加时间" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="添加时间"></el-input>
    </el-form-item>
    <el-form-item label="修改时间" prop="updateTime">
      <el-input v-model="dataForm.updateTime" placeholder="修改时间"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          blogId: 0,
          blogTitle: '',
          blogSubUrl: '',
          blogCoverImage: '',
          blogContent: '',
          blogCategoryId: '',
          blogCategoryName: '',
          blogTags: '',
          blogStatus: '',
          blogViews: '',
          enableComment: '',
          isDeleted: '',
          createTime: '',
          updateTime: ''
        },
        dataRule: {
          blogTitle: [
            { required: true, message: '博客标题不能为空', trigger: 'blur' }
          ],
          blogSubUrl: [
            { required: true, message: '博客自定义路径url不能为空', trigger: 'blur' }
          ],
          blogCoverImage: [
            { required: true, message: '博客封面图不能为空', trigger: 'blur' }
          ],
          blogContent: [
            { required: true, message: '博客内容不能为空', trigger: 'blur' }
          ],
          blogCategoryId: [
            { required: true, message: '博客分类id不能为空', trigger: 'blur' }
          ],
          blogCategoryName: [
            { required: true, message: '博客分类(冗余字段)不能为空', trigger: 'blur' }
          ],
          blogTags: [
            { required: true, message: '博客标签不能为空', trigger: 'blur' }
          ],
          blogStatus: [
            { required: true, message: '0-草稿 1-发布不能为空', trigger: 'blur' }
          ],
          blogViews: [
            { required: true, message: '阅读量不能为空', trigger: 'blur' }
          ],
          enableComment: [
            { required: true, message: '0-允许评论 1-不允许评论不能为空', trigger: 'blur' }
          ],
          isDeleted: [
            { required: true, message: '是否删除 0=否 1=是不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '添加时间不能为空', trigger: 'blur' }
          ],
          updateTime: [
            { required: true, message: '修改时间不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.blogId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.blogId) {
            this.$http({
              url: this.$http.adornUrl(`/blog/blog/info/${this.dataForm.blogId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.blogTitle = data.blog.blogTitle
                this.dataForm.blogSubUrl = data.blog.blogSubUrl
                this.dataForm.blogCoverImage = data.blog.blogCoverImage
                this.dataForm.blogContent = data.blog.blogContent
                this.dataForm.blogCategoryId = data.blog.blogCategoryId
                this.dataForm.blogCategoryName = data.blog.blogCategoryName
                this.dataForm.blogTags = data.blog.blogTags
                this.dataForm.blogStatus = data.blog.blogStatus
                this.dataForm.blogViews = data.blog.blogViews
                this.dataForm.enableComment = data.blog.enableComment
                this.dataForm.isDeleted = data.blog.isDeleted
                this.dataForm.createTime = data.blog.createTime
                this.dataForm.updateTime = data.blog.updateTime
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/blog/blog/${!this.dataForm.blogId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'blogId': this.dataForm.blogId || undefined,
                'blogTitle': this.dataForm.blogTitle,
                'blogSubUrl': this.dataForm.blogSubUrl,
                'blogCoverImage': this.dataForm.blogCoverImage,
                'blogContent': this.dataForm.blogContent,
                'blogCategoryId': this.dataForm.blogCategoryId,
                'blogCategoryName': this.dataForm.blogCategoryName,
                'blogTags': this.dataForm.blogTags,
                'blogStatus': this.dataForm.blogStatus,
                'blogViews': this.dataForm.blogViews,
                'enableComment': this.dataForm.enableComment,
                'isDeleted': this.dataForm.isDeleted,
                'createTime': this.dataForm.createTime,
                'updateTime': this.dataForm.updateTime
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
