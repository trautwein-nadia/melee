package com.meleeChat.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Tournament {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("tournament_type")
    @Expose
    public String tournamentType;
    @SerializedName("started_at")
    @Expose
    public String startedAt;
    @SerializedName("completed_at")
    @Expose
    public Object completedAt;
    @SerializedName("require_score_agreement")
    @Expose
    public Boolean requireScoreAgreement;
    @SerializedName("notify_users_when_matches_open")
    @Expose
    public Boolean notifyUsersWhenMatchesOpen;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("open_signup")
    @Expose
    public Boolean openSignup;
    @SerializedName("notify_users_when_the_tournament_ends")
    @Expose
    public Boolean notifyUsersWhenTheTournamentEnds;
    @SerializedName("progress_meter")
    @Expose
    public Integer progressMeter;
    @SerializedName("quick_advance")
    @Expose
    public Boolean quickAdvance;
    @SerializedName("hold_third_place_match")
    @Expose
    public Boolean holdThirdPlaceMatch;
    @SerializedName("pts_for_game_win")
    @Expose
    public String ptsForGameWin;
    @SerializedName("pts_for_game_tie")
    @Expose
    public String ptsForGameTie;
    @SerializedName("pts_for_match_win")
    @Expose
    public String ptsForMatchWin;
    @SerializedName("pts_for_match_tie")
    @Expose
    public String ptsForMatchTie;
    @SerializedName("pts_for_bye")
    @Expose
    public String ptsForBye;
    @SerializedName("swiss_rounds")
    @Expose
    public Integer swissRounds;
    @SerializedName("private")
    @Expose
    public Boolean _private;
    @SerializedName("ranked_by")
    @Expose
    public String rankedBy;
    @SerializedName("show_rounds")
    @Expose
    public Boolean showRounds;
    @SerializedName("hide_forum")
    @Expose
    public Boolean hideForum;
    @SerializedName("sequential_pairings")
    @Expose
    public Boolean sequentialPairings;
    @SerializedName("accept_attachments")
    @Expose
    public Boolean acceptAttachments;
    @SerializedName("rr_pts_for_game_win")
    @Expose
    public String rrPtsForGameWin;
    @SerializedName("rr_pts_for_game_tie")
    @Expose
    public String rrPtsForGameTie;
    @SerializedName("rr_pts_for_match_win")
    @Expose
    public String rrPtsForMatchWin;
    @SerializedName("rr_pts_for_match_tie")
    @Expose
    public String rrPtsForMatchTie;
    @SerializedName("created_by_api")
    @Expose
    public Boolean createdByApi;
    @SerializedName("credit_capped")
    @Expose
    public Boolean creditCapped;
    @SerializedName("category")
    @Expose
    public Object category;
    @SerializedName("hide_seeds")
    @Expose
    public Boolean hideSeeds;
    @SerializedName("prediction_method")
    @Expose
    public Integer predictionMethod;
    @SerializedName("predictions_opened_at")
    @Expose
    public Object predictionsOpenedAt;
    @SerializedName("anonymous_voting")
    @Expose
    public Boolean anonymousVoting;
    @SerializedName("max_predictions_per_user")
    @Expose
    public Integer maxPredictionsPerUser;
    @SerializedName("signup_cap")
    @Expose
    public Object signupCap;
    @SerializedName("game_id")
    @Expose
    public Integer gameId;
    @SerializedName("participants_count")
    @Expose
    public Integer participantsCount;
    @SerializedName("group_stages_enabled")
    @Expose
    public Boolean groupStagesEnabled;
    @SerializedName("allow_participant_match_reporting")
    @Expose
    public Boolean allowParticipantMatchReporting;
    @SerializedName("teams")
    @Expose
    public Boolean teams;
    @SerializedName("check_in_duration")
    @Expose
    public Object checkInDuration;
    @SerializedName("start_at")
    @Expose
    public Object startAt;
    @SerializedName("started_checking_in_at")
    @Expose
    public Object startedCheckingInAt;
    @SerializedName("tie_breaks")
    @Expose
    public List<String> tieBreaks = new ArrayList<String>();
    @SerializedName("locked_at")
    @Expose
    public Object lockedAt;
    @SerializedName("event_id")
    @Expose
    public Object eventId;
    @SerializedName("public_predictions_before_start_time")
    @Expose
    public Boolean publicPredictionsBeforeStartTime;
    @SerializedName("ranked")
    @Expose
    public Boolean ranked;
    @SerializedName("description_source")
    @Expose
    public String descriptionSource;
    @SerializedName("subdomain")
    @Expose
    public String subdomain;
    @SerializedName("full_challonge_url")
    @Expose
    public String fullChallongeUrl;
    @SerializedName("live_image_url")
    @Expose
    public String liveImageUrl;
    @SerializedName("sign_up_url")
    @Expose
    public Object signUpUrl;
    @SerializedName("review_before_finalizing")
    @Expose
    public Boolean reviewBeforeFinalizing;
    @SerializedName("accepting_predictions")
    @Expose
    public Boolean acceptingPredictions;
    @SerializedName("participants_locked")
    @Expose
    public Boolean participantsLocked;
    @SerializedName("game_name")
    @Expose
    public String gameName;
    @SerializedName("participants_swappable")
    @Expose
    public Boolean participantsSwappable;
    @SerializedName("team_convertable")
    @Expose
    public Boolean teamConvertable;
    @SerializedName("group_stages_were_started")
    @Expose
    public Boolean groupStagesWereStarted;

}