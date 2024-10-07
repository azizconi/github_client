package com.example.githubclient.feature_app.domain.entity.search_history

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.githubclient.feature_app.data.remote.response.github_repository.GithubRepositoryResponseModel
import com.example.githubclient.feature_app.data.remote.response.github_repository.License
import com.example.githubclient.feature_app.data.remote.response.github_repository.Owner

@Entity(tableName = "github_repositories")
data class SearchHistoryEntity(
    @PrimaryKey
    val id: Int,
    val allow_forking: Boolean?,
    val archive_url: String?,
    val archived: Boolean?,
    val assignees_url: String?,
    val blobs_url: String?,
    val branches_url: String?,
    val clone_url: String?,
    val collaborators_url: String?,
    val comments_url: String?,
    val commits_url: String?,
    val compare_url: String?,
    val contents_url: String?,
    val contributors_url: String?,
    val created_at: String?,
    val default_branch: String?,
    val deployments_url: String?,
    val description: String?,
    val disabled: Boolean?,
    val downloads_url: String?,
    val events_url: String?,
    val fork: Boolean?,
    val forks: Int?,
    val forks_count: Int?,
    val forks_url: String?,
    val full_name: String?,
    val git_commits_url: String?,
    val git_refs_url: String?,
    val git_tags_url: String?,
    val git_url: String?,
    val has_discussions: Boolean?,
    val has_downloads: Boolean?,
    val has_issues: Boolean?,
    val has_pages: Boolean?,
    val has_projects: Boolean?,
    val has_wiki: Boolean?,
    val homepage: String?,
    val hooks_url: String,
    val html_url: String,
    val is_template: Boolean?,
    val issue_comment_url: String?,
    val issue_events_url: String?,
    val issues_url: String?,
    val keys_url: String?,
    val labels_url: String?,
    val language: String?,
    val languages_url: String?,
    val license: License?,
    val merges_url: String?,
    val milestones_url: String?,
    val name: String?,
    val node_id: String?,
    val notifications_url: String?,
    val open_issues: Int?,
    val open_issues_count: Int?,
    val owner: Owner?,
    val `private`: Boolean?,
    val pulls_url: String?,
    val pushed_at: String?,
    val releases_url: String?,
    val score: Double?,
    val size: Int?,
    val ssh_url: String?,
    val stargazers_count: Int?,
    val stargazers_url: String?,
    val statuses_url: String?,
    val subscribers_url: String?,
    val subscription_url: String?,
    val svn_url: String?,
    val tags_url: String?,
    val teams_url: String?,
    val topics: List<String>,
    val trees_url: String?,
    val updated_at: String?,
    val url: String?,
    val visibility: String?,
    val watchers: Int?,
    val watchers_count: Int?,
    val web_commit_signoff_required: Boolean?,

    val created: Long,
    val updated: Long,
) {
    fun toResponseModel(): GithubRepositoryResponseModel {
        return GithubRepositoryResponseModel(
            id = id,
            allow_forking = allow_forking,
            archive_url = archive_url,
            archived = archived,
            assignees_url = assignees_url,
            blobs_url = blobs_url,
            branches_url = branches_url,
            clone_url = clone_url,
            collaborators_url = collaborators_url,
            comments_url = comments_url,
            commits_url = commits_url,
            compare_url = compare_url,
            contents_url = contents_url,
            contributors_url = contributors_url,
            created_at = created_at,
            default_branch = default_branch,
            deployments_url = deployments_url,
            description = description,
            disabled = disabled,
            downloads_url = downloads_url,
            events_url = events_url,
            fork = fork,
            forks = forks,
            forks_count = forks_count,
            forks_url = forks_url,
            full_name = full_name,
            git_commits_url = git_commits_url,
            git_refs_url = git_refs_url,
            git_tags_url = git_tags_url,
            git_url = git_url,
            has_discussions = has_discussions,
            has_downloads = has_downloads,
            has_issues = has_issues,
            has_pages = has_pages,
            has_projects = has_projects,
            has_wiki = has_wiki,
            homepage = homepage,
            hooks_url = hooks_url,
            html_url = html_url,
            is_template = is_template,
            issue_comment_url = issue_comment_url,
            issue_events_url = issue_events_url,
            issues_url = issues_url,
            keys_url = keys_url,
            labels_url = labels_url,
            language = language,
            languages_url = languages_url,
            license = license,
            merges_url = merges_url,
            milestones_url = milestones_url,
            name = name,
            node_id = node_id,
            notifications_url = notifications_url,
            open_issues = open_issues,
            open_issues_count = open_issues_count,
            owner = owner,
            pulls_url = pulls_url,
            pushed_at = pushed_at,
            releases_url = releases_url,
            score = score,
            size = size,
            ssh_url = ssh_url,
            stargazers_count = stargazers_count,
            stargazers_url = stargazers_url,
            statuses_url = statuses_url,
            subscribers_url = subscribers_url,
            subscription_url = subscription_url,
            svn_url = svn_url,
            tags_url = tags_url,
            teams_url = teams_url,
            topics = topics,
            trees_url = trees_url,
            updated_at = updated_at,
            url = url,
            visibility = visibility,
            watchers = watchers,
            watchers_count = watchers_count,
            web_commit_signoff_required = web_commit_signoff_required,
            private = private
        )
    }
}
