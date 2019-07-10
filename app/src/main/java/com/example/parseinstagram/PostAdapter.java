package com.example.parseinstagram;

//public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{
//    private ArrayList<Post> mPosts;
//    private Context mContext;
//    private String mUserId;
//
//    public PostAdapter(Context context, String userId, ArrayList<Post> posts) {
//        mPosts = posts;
//        this.mUserId = userId;
//        mContext = context;
//    }
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Context context = parent.getContext();
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View contactView = inflater.inflate(R.layout.item_post, parent,false);
//
//        ViewHolder viewHolder = new ViewHolder(contactView);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
//        Post post = mPosts.get(position);
//        final boolean isMe = post.getUser() != null && post.getUser().equals(mUserId);
//
//        if (isMe) {
//            holder.
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//        }
//    }
//}
//
