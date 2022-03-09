<template>
  <el-dialog
    :title="!dataForm.commentId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="关联的blog主键" prop="blogId">
      <el-input v-model="dataForm.blogId" placeholder="关联的blog主键"></el-input>
    </el-form-item>
    <el-form-item label="评论者名称" prop="commentator">
      <el-input v-model="dataForm.commentator" placeholder="评论者名称"></el-input>
    </el-form-item>
    <el-form-item label="评论人的邮箱" prop="email">
      <el-input v-model="dataForm.email" placeholder="评论人的邮箱"></el-input>
    </el-form-item>
    <el-form-item label="网址" prop="websiteUrl">
      <el-input v-model="dataForm.websiteUrl" placeholder="网址"></el-input>
    </el-form-item>
    <el-form-item label="评论内容" prop="commentBody">
      <el-input v-model="dataForm.commentBody" placeholder="评论内容"></el-input>
    </el-form-item>
    <el-form-item label="评论提交时间" prop="commentCreateTime">
      <el-input v-model="dataForm.commentCreateTime" placeholder="评论提交时间"></el-input>
    </el-form-item>
    <el-form-item label="评论时的ip地址" prop="commentatorIp">
      <el-input v-model="dataForm.commentatorIp" placeholder="评论时的ip地址"></el-input>
    </el-form-item>
    <el-form-item label="回复内容" prop="replyBody">
      <el-input v-model="dataForm.replyBody" placeholder="回复内容"></el-input>
    </el-form-item>
    <el-form-item label="回复时间" prop="replyCreateTime">
      <el-input v-model="dataForm.replyCreateTime" placeholder="回复时间"></el-input>
    </el-form-item>
    <el-form-item label="是否审核通过 0-未审核 1-审核通过" prop="commentStatus">
      <el-input v-model="dataForm.commentStatus" placeholder="是否审核通过 0-未审核 1-审核通过"></el-input>
    </el-form-item>
    <el-form-item label="是否删除 0-未删除 1-已删除" prop="isDeleted">
      <el-input v-model="dataForm.isDeleted" placeholder="是否删除 0-未删除 1-已删除"></el-input>
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
          commentId: 0,
          blogId: '',
          commentator: '',
          email: '',
          websiteUrl: '',
          commentBody: '',
          commentCreateTime: '',
          commentatorIp: '',
          replyBody: '',
          replyCreateTime: '',
          commentStatus: '',
          isDeleted: ''
        },
        dataRule: {
          blogId: [
            { required: true, message: '关联的blog主键不能为空', trigger: 'blur' }
          ],
          commentator: [
            { required: true, message: '评论者名称不能为空', trigger: 'blur' }
          ],
          email: [
            { required: true, message: '评论人的邮箱不能为空', trigger: 'blur' }
          ],
          websiteUrl: [
            { required: true, message: '网址不能为空', trigger: 'blur' }
          ],
          commentBody: [
            { required: true, message: '评论内容不能为空', trigger: 'blur' }
          ],
          commentCreateTime: [
            { required: true, message: '评论提交时间不能为空', trigger: 'blur' }
          ],
          commentatorIp: [
            { required: true, message: '评论时的ip地址不能为空', trigger: 'blur' }
          ],
          replyBody: [
            { required: true, message: '回复内容不能为空', trigger: 'blur' }
          ],
          replyCreateTime: [
            { required: true, message: '回复时间不能为空', trigger: 'blur' }
          ],
          commentStatus: [
            { required: true, message: '是否审核通过 0-未审核 1-审核通过不能为空', trigger: 'blur' }
          ],
          isDeleted: [
            { required: true, message: '是否删除 0-未删除 1-已删除不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.commentId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.commentId) {
            this.$http({
              url: this.$http.adornUrl(`/blog/blogcomment/info/${this.dataForm.commentId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.blogId = data.blogComment.blogId
                this.dataForm.commentator = data.blogComment.commentator
                this.dataForm.email = data.blogComment.email
                this.dataForm.websiteUrl = data.blogComment.websiteUrl
                this.dataForm.commentBody = data.blogComment.commentBody
                this.dataForm.commentCreateTime = data.blogComment.commentCreateTime
                this.dataForm.commentatorIp = data.blogComment.commentatorIp
                this.dataForm.replyBody = data.blogComment.replyBody
                this.dataForm.replyCreateTime = data.blogComment.replyCreateTime
                this.dataForm.commentStatus = data.blogComment.commentStatus
                this.dataForm.isDeleted = data.blogComment.isDeleted
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
              url: this.$http.adornUrl(`/blog/blogcomment/${!this.dataForm.commentId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'commentId': this.dataForm.commentId || undefined,
                'blogId': this.dataForm.blogId,
                'commentator': this.dataForm.commentator,
                'email': this.dataForm.email,
                'websiteUrl': this.dataForm.websiteUrl,
                'commentBody': this.dataForm.commentBody,
                'commentCreateTime': this.dataForm.commentCreateTime,
                'commentatorIp': this.dataForm.commentatorIp,
                'replyBody': this.dataForm.replyBody,
                'replyCreateTime': this.dataForm.replyCreateTime,
                'commentStatus': this.dataForm.commentStatus,
                'isDeleted': this.dataForm.isDeleted
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
