package com.dell.bitbucket.sanity.merge.hook;

import com.atlassian.stash.hook.repository.*;
import com.atlassian.stash.pull.PullRequestParticipant;
import com.atlassian.stash.repository.Repository;
import com.atlassian.stash.setting.*;

import java.util.regex.Pattern;

public class sanityTriggerCheck implements RepositoryMergeRequestCheck, RepositorySettingsValidator
{
    private static final Pattern NUMBER_PATTERN = Pattern.compile("[0-9]+");

    /**
     * Vetos a pull-request if there aren't enough reviewers.
     */
    @Override
    public void check(RepositoryMergeRequestCheckContext context)
    {
        int requiredReviewers = Integer.parseInt(context.getSettings().getString("reviewers"));
        int acceptedCount = 0;
        for (PullRequestParticipant reviewer : context.getMergeRequest().getPullRequest().getReviewers())
        {
            acceptedCount = acceptedCount + (reviewer.isApproved() ? 1 : 0);
        }
        if (acceptedCount < requiredReviewers)
        {
            context.getMergeRequest().veto("Not enough approved reviewers", acceptedCount + " reviewers have approved your pull request. You need " + requiredReviewers + " (total) before you may merge.");
        }
    }

    @Override
    public void validate(Settings settings, SettingsValidationErrors errors, Repository repository)
    {
        String numReviewersString = settings.getString("reviewers", "0").trim();
        if (!NUMBER_PATTERN.matcher(numReviewersString).matches())
        {
            errors.addFieldError("reviewers", "Enter a number");
        }
        else if (Integer.parseInt(numReviewersString) <= 0)
        {
            errors.addFieldError("reviewers", "Number of reviewers must be greater than zero");
        }
    }
}
